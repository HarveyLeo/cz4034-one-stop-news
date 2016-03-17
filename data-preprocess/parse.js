var fs = require('fs');
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


        
    });
    return data;
}
var finalData = null;

for (var src of ['bbc-updated.json', 'cnn-updated.json', 'guardian-updated.json', 'reuters-updated.json', 'straits-times-updated.json']) {
    var data = fs.readFileSync(src, "utf8");
    data = JSON.parse(data);
    data = cleanData(data);

    if (finalData === null) {
        finalData = data;
    } else {
        finalData = finalData.concat(data);
    }
    
}

fs.writeFileSync("data.json", JSON.stringify(finalData, null, 4)); 