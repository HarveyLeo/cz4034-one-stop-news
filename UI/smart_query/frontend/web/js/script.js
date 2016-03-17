$("#query_button").click(function(){
    var query = $("#search_box").val();
    var url = 'http://solr.kenrick95.xyz/solr/cz4034/select?q=message%3A' +  query + '&wt=json&indent=true';
    if($("#queryform-category").find(".active").length == 1){
        var category = $("#queryform-category").find(".active").html();
        var n = category.split(" ");
        category = n[n.length - 1];

         url += '&fq=source:' + category;
    }

    if($("#sort_by").val() == "Latest"){
        url += "&sort=created_time+desc";
    }
    else if($("#sort_by").val() == "Popularity"){
        url += "&sort=like_count+desc";
    }
    $.ajax({
        type     :'POST',
        dataType: 'jsonp',
        jsonp : 'json.wrf',
        cache    : false,
        url  : url,
        success  : function(response) {
            console.log(typeof(response.response.docs));
            var json_data = JSON.stringify(response.response.docs);

            $("#hi_data").val(json_data);
            $("#query_form").submit();

        }
    });

});