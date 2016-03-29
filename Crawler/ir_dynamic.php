<?php

$news= $_POST["news"];
$tail="&access_token=".$_POST["accesscode"];
$filename = $news.".txt";
$max=50;
$header="https://api.facebook.com/method/fql.query?format=json-strings&query=";
//$tail ="&access_token=CAACEdEose0cBAKEkJn36B6YwHjigwH4F1gRrSRgAeZBY11LIZBFx8s2tQkI15ZACVHKZCPG0ghXXkgRgQ5Ijc1evYZAa6gK92Q9aSvZA4LE2oZChNlwU3eMmbY0naQZCrB7uUUo39AOMLLZC6qsnOYiYz0f0sNL3UO1tsW2WTLkKUIiD2CKm9MA0wZA6zCgENJfhEZBYON8BwowZCAZDZD";
    
//set timestamp
$time =fopen($filename,r);
$timestamp =fgets($time);
//echo "<br><br>$timestamp";
fclose($time);
	$name =$news.".json";
	$file = fopen($name,"r");
	$text = fread($file,filesize($name));
	fclose($file);

//Determine Source id
	if ($news=="straits-times")
	{
		$source = 129011692114;
	}
	else if ($news=="cnn")
	{
		$source = 5550296508;
	}
	else if ($news=="bbc")
	{
		$source=228735667216;
	}
	else if ($news=="reuters")
	{
		$source=114050161948682;
	}
	else{
		$source=10513336322;
	}
	

	$try= $header."SELECT%20post_id,created_time,attachment,message,like_info%20FROM%20stream%20WHERE%20source_id=".$source."%20%20and%20created_time>".$timestamp."%20".$tail;	
    echo "$try<br><br>";
	$json = file_get_contents($try);
	$json_output = json_decode($json);
	
	$max = SIZEOF($json_output);
	

	$dunno = get_object_vars($json_output[0]);
	$timestamp = $dunno[created_time];
	
	if ($timestamp!="")
	{
	$time =fopen($filename,w);
	fwrite ($time, $timestamp);
	fclose($time);
	}

	
	$revised = json_encode($json_output);
/*

	$file = fopen($name,"w");
	if (strlen($text)>1)
	{
	$revised = substr($revised,0,strlen($revised)-2);
	}
	if (strlen($revised)>1)
	{
	$text=substr($text,1);
	}
	$finalwrite = $revised.",".$text;

	echo fwrite($file,$finalwrite);
*/
	$url = 'http://solr.kenrick95.xyz:82/classify';
	$data = array('text' => $revised, 'filename' => $news);

	// use key 'http' even if you send the request to https://...
	$options = array(
		'http' => array(
			'method'  => 'POST',
			'content' => http_build_query($data)
		)
	);
	$context  = stream_context_create($options);
	$result = file_get_contents($url, false, $context);
	if ($result === FALSE) { /* Handle error */ }

	var_dump($result);
	echo '<script type="text/javascript">alert("' . $max ."entries has been added to ". $news ." database" . '");
	window.location.href=\'crawl.html\';
	
	</script>';

	//print_r($json_output);


	

	
	
	
	
error_reporting(E_ALL);
?>	
	
	