# Preprocess

## Posting file
`POST http://solr.kenrick95.xyz:3000/file`

Fields:
- `text`: file content, should be in valid JSON
- `filename`: filename, without extension; must be one of these
    + `bbc-updated`
    + `cnn-updated`
    + `guardian-updated`
    + `reuters-updated`
    + `straits-times-updated`

Then, `filename.json` will be saved in the server.

## Parsing
`GET http://solr.kenrick95.xyz:3000/parse`

- Parse data from:
    + `bbc-updated.json`
    + `cnn-updated.json`
    + `guardian-updated.json`
    + `reuters-updated.json`
    + `straits-times-updated.json`
- And then `data-stemmed.json` will be generated. Hopefully, no error will be seen.

## Posting to Solr
`GET http://solr.kenrick95.xyz:3000/post`

- Wait for it!
- Done~