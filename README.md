# cz4034-information-retrieval

In this assignment, we <br />
1) crawl a text corpus from Facebook pages of The Guardian, The Straits Times, and BBC News, <br />
2) build a search engine to query over the corpus, and <br />
3) perform news category classification on the posts. <br />


## Notes to Robert:
- Select by soruce: `fq=source:BBC`. Example: http://solr.kenrick95.xyz/solr/cz4034/select?q=message%3AISIS&fq=source%3ABBC&wt=json&indent=true
- Select from category: `fq=category:Politics`. Note: `category` is case-sensitive. Example: http://solr.kenrick95.xyz/solr/cz4034/select?q=message%3AObama&fq=category%3APolitics&wt=json&indent=true
- Sort by time `sort=created_time desc`. Example: http://solr.kenrick95.xyz/solr/cz4034/select?q=message%3AObama&sort=created_time+desc&wt=json&indent=true
- Sort by popularity `sort=like_count desc`. Example: http://solr.kenrick95.xyz/solr/cz4034/select?q=message%3AObama&sort=like_count+desc&wt=json&indent=true
- Default sort is by relevance
- Spell check: `/spell?q=message:Obame` http://solr.kenrick95.xyz/solr/cz4034/spell?q=message%3AObame&rows=0&wt=json&indent=true
- Paging:
  - Page 1, 10 results: `start=0&rows=10` http://solr.kenrick95.xyz/solr/cz4034/select?q=message%3Aobama&start=0&rows=10&wt=json&indent=true
  - Page 2, 10 more results: `start=1&rows=10` http://solr.kenrick95.xyz/solr/cz4034/select?q=message%3Aobama&start=1&rows=10&wt=json&indent=true

### Notes 2:
- Query at `attachment_name` and `attachment_description` too: `message:Bhutan OR attachment_name:Bhutan OR attachment_description:Bhutan` http://solr.kenrick95.xyz/solr/cz4034/select?q=message%3ABhutan+OR+attachment_name%3ABhutan&start=0&rows=10&wt=json&indent=true
- Suggestor: `/suggest?suggest.q=Obam`. Example: http://solr.kenrick95.xyz/solr/cz4034/suggest?wt=json&indent=true&suggest.q=Obam
- Increase `click_count`, replace the `post_id` enclosed with %22. http://solr.kenrick95.xyz/solr/cz4034/update?stream.body=[{%22post_id%22:%22228735667216_10153316975397217%22,%22click_count%22:{%22inc%22:1}}]&commit=true
