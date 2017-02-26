require 'rubygems'
require 'bundler/setup'

require_relative "sentence.pb.rb"
require_relative 'elastic_client.rb'
require 'erb'
require 'pry'


class ExtWikilinksReader

  def self.calc_erb(from_file, to_file, varible, mentions)
    erb = ERB.new(File.open("#{__dir__}/index.html.erb").read)
    File.write(to_file, erb.result(binding))
  end

  def self.read_sentence(stream)
    return nil if stream.eof?
    length = Protobuf::Varint.decode(stream)
    Sentence.decode(stream.read(length))
  end

  def self.skip(stream, n)
    n.times{ read_sentence(stream) }
  end

  def self.render_mention(sentence)
    prev = []
    mentions = sentence.mentions.map{|x| [x.id, x]}.to_h
    sentence.parse_result.map do |token|
      opened_tags = token.mentions - prev
      closed_tags = prev - token.mentions

      opened_tags_str = opened_tags.map do |x|
        mention = mentions[x]
        "<span mention_id='#{x}' class=\"mention_#{mention.resolver} mention\">"
      end.join

      closed_tags_str = closed_tags.map{|x| "</span>" }.join
      prev = token.mentions

      "#{closed_tags_str}#{opened_tags_str}#{token.token}"
    end.join(" ") + "</span>" * prev.size
  end

end


def render
  fstream = File.open("../resources/worker_6_sentences.pb")
  ExtWikilinksReader.skip(fstream, (ARGV[0] || 0).to_i)
  sentence = ExtWikilinksReader.read_sentence(fstream)
  if sentence.parser_name == "None" then
    p sentence
  else
    mentions = sentence.mentions.map{|x| [x.id, x]}.to_h
    ExtWikilinksReader.calc_erb("./index.html.erb", "./www/index.html", ExtWikilinksReader.render_mention(sentence), mentions)
  end

  sentence.parse_result.each{|x| p x}
end


def read_files(fnames)
  Enumerator.new do |en|
    fnames.each do |fname|
      read_mentions(fname).each do |sent|
        en << sent.to_hash
      end
    end
  end
end

def read_mentions(fname)
  fstream = File.open(fname)
  Enumerator.new do |en|
    while snt = ExtWikilinksReader.read_sentence(fstream)
      en << snt
    end
  end
end


def main
 enum = read_files(ARGV)
 el = WikiSeracher.new

 enum.each_slice(10000).with_index do |buffer, idx|
   puts "Loading: #{idx}"
   el.add_records(buffer, index: 'sknn-data', type: 'sentence')
 end
end

if $0 == __FILE__
  main
end

