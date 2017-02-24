require 'rubygems'
require 'bundler/setup'
require 'elasticsearch'

class WikiSeracher

  def initialize
    @client = Elasticsearch::Client.new log: false
  end

  def add_mention(mention)
    @client.index index: 'wiki', type: 'mention', body: mention
  end


  def add_records(records, index:, type:)
    @client.bulk body: records.map do |record|
      {
        index: {
          _index: index,
          _type: type,
          data: record
        }
      }
    end
  end
end
