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
