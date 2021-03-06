require 'rubygems'
require 'bundler/setup'
require 'elasticsearch'

class WikiSeracher

  attr_accessor :client

  def initialize
    @client = Elasticsearch::Client.new log: false
  end

  def add_record(record, index:, type:)
    @client.index index: index, type: type, body: record
  end


  def gen_data(records, index, type)
    records.map do |record|
      {
        index: {
          _index: index,
          _type: type,
          data: record
        }
      }
    end
  end

  def add_records(records, index:, type:)
    @client.bulk body: gen_data(records, index, type)
  end
end
