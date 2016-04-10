var express = require('express');
var app = express();
var fs = require('fs');
var stemmer = require('stemmer');
var request = require('request');
var bodyParser = require("body-parser");
app.use(bodyParser.urlencoded({ limit: '50mb', extended: true }));
app.use(bodyParser.json({limit: '50mb'}));


app.post('/file', function (req, res) {
    var filename = req.body.filename,
        text = req.body.text,
        newPath = __dirname + "/" + filename + ".json";

    if (['bbc-updated',
        'cnn-updated',
        'guardian-updated',
        'reuters-updated',
        'straits-times-updated'].indexOf(filename) === -1) {
        console.log(req);
        res.send("Not OK");
    } else {
        fs.writeFile(newPath, text, function (err) {
            console.log(err);
            res.send(err);
        });
    }
    

});
app.get('/parse', function (req, res) {
    var finalData = null;
    function cleanData(data) {
        data.forEach((v, i) => {
            if ('attachment' in v) {
                for (var k in v.attachment) {
                    v['attachment_' + k] = v.attachment[k];
                }
                delete v.attachment;

                for (var x in v.attachment_media) {
                    for (var k in v.attachment_media[x]) {
                        v['attachment_media_' + k] = v.attachment_media[x][k];
                    }
                }
                delete v.attachment_media;

                for (var k in v.attachment_media_photo) {
                    v['attachment_media_photo_' + k] = v.attachment_media_photo[k];
                }
                delete v.attachment_media_photo;

                if ('attachment_media_photo_images' in v) {
                    for (var x in v.attachment_media_photo_images) {
                        for (var k in v.attachment_media_photo_images[x]) {
                            if (!('attachment_media_photo_images_' + k in v)) {
                                v['attachment_media_photo_images_' + k] = [];
                            }
                            v['attachment_media_photo_images_' + k].push(v.attachment_media_photo_images[x][k]);
                        }
                    }
                    delete v.attachment_media_photo_images;
                }
                

                if ('attachment_properties' in v) {
                    for (var k in v.attachment_properties[0]) {
                        v['attachment_properties_' + k] = v.attachment_properties[0][k];
                    }
                }
                
                delete v.attachment_properties;

                for (var k in v.attachment_media_video) {
                    v['attachment_media_video_' + k] = v.attachment_media_video[k];
                }
                delete v.attachment_media_video;

                delete v.attachment_tagged_ids;
            }
            v.like_count = v.like_info.like_count;
            delete v.like_info;

            v.stemmed_message = v.message.split(/[\s,#.]+/).reduce(function(previousValue, currentValue, currentIndex, array) {
                return previousValue + " " + stemmer(currentValue);
            });

        });


        return data;
    }

    for (var src of ['bbc-updated.json', 'cnn-updated.json', 'guardian-updated.json', 'reuters-updated.json', 'straits-times-updated.json']) {
        var data = fs.readFileSync(src, "utf8");
        data = JSON.parse(data.toString('utf8').replace(/^\uFEFF/, ''));
        data = cleanData(data);

        if (finalData === null) {
            finalData = data;
        } else {
            finalData = finalData.concat(data);
        }
        
    }

    fs.writeFileSync("data-stemmed.json", JSON.stringify(finalData));
    fs.writeFileSync("data-stemmed-pretty.json", JSON.stringify(finalData, null, 4));

    res.send('OK');
});
app.get('/view', function (req, res) {
    var text = fs.readFileSync("data-stemmed.json", "utf8");
    res.send(text);
    
});
app.get('/post', function (req, res) {
    // http://solr.kenrick95.xyz/solr/cz4034/update?stream.body=&commit=true&wt=json&stream.contentType=application/json
    
    var text = fs.readFileSync("data-stemmed.json", "utf8");
    request.post(
        'http://192.168.109.2/solr/cz4034/update?commit=true&commitWithin=1000&wt=json',
        {
            json: JSON.parse(text)
        },
        function (error, response, body) {
            response.setEncoding('utf-8');

            // console.log("**** error: ");
            console.log("**** response: ");
            console.log(response);
            console.log("**** body: ");
            console.log(body);
            if (!error && response.statusCode == 200) {
                res.send(body);
            } else {
                console.log(error);
                res.send(body);
            }
        }
    );
    
});

app.listen(3000, function () {
    console.log('Example app listening on port 3000!');
});
