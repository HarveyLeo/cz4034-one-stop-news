<?php

    use kartik\select2\Select2;
    use kartik\widgets\ActiveForm;
    use yii\widgets\ListView;
    use yii\helpers\Html;/* @var $this yii\web\View */
    $data = ['School', 'Academic', 'Career' , 'Love', 'Campus Life'];
    $this->title = 'My Yii Application';

?>

<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.0.0-beta1/jquery.min.js"></script>
<div class="site-index">
    <div class="col-md-3">

    </div>

    <div class="col-md-7">
        <?php $form = ActiveForm::begin() ?>
            <div class="row">
                <div class="col-md-8">
                    <?= $form->field($query_form, 'category')->checkboxButtonGroup($data)->label(false) ?>
                </div>
                <div class="col-md-4">
                    <?= $form->field($query_form, 'sentiment_analysis')->widget(Select2::classname(), [
                        'data' => ['Positive' => 'Positive', 'Negative' => 'Negative'],
                        'language' => 'de',
                        'options' => ['placeholder' => 'Select sentiment Analysis ...'],
                        'pluginOptions' => [
                            'allowClear' => true
                        ],
                    ])->label(false) ?>
                </div>
            </div>
        <br>
        <br>


            <div class="row">
                <div class="col-md-8">
                    <?= Select2::widget([
                        'name' => 'state_2',
                        'id' => 'search_box',
                        'data' => ['Agree' => 'Agree' , 'Disagree' => 'Disagree'],
                        'pluginOptions' => ['allowClear' => true, 'tags' => true],
                        'options' => [ 'placeholder' => 'Search here ...']
                    ]) ?>
                </div>
                <div class="col-md-4">

                    <?= Html::button('Search', ['class' => 'btn btn-primary', 'id' => 'search_button']) ?>

                </div>

            </div>
            <hr>
            <div class="row" align="center">
                <label>Query Result</label>
            </div>
            <br>
            <div class="row" id="list_data">
                <?= ListView::widget([
                    'id' => 'threadList',
                    'dataProvider' => $list_data_provider,
                    'pager' => ['class' => \kop\y2sp\ScrollPager::className()],
                    'summary' => false,
                    'itemOptions' => ['class' => 'item'],
                    'layout' => "{summary}\n{items}\n{pager}",
                    'itemView' => function ($model, $key, $index, $widget) {
                        return $this->render('_list_query',['model' => $model]);
                    }
                ])
                ?>
            </div>
        <?php $form = ActiveForm::end() ?>


    </div>
</div>

<script type="text/javascript">
    //get the click of modal button to create / update item
    //we get the button by class not by ID because you can only have one id on a page and you can
    //have multiple classes therefore you can have multiple open modal buttons on a page all with or without
    //the same link.
    //we use on so the dom element can be called again if they are nested, otherwise when we load the content once it kills the dom element and wont let you load anther modal on click without a page refresh
    $(document).on('click', '#search_button', function(){
        //check if the modal is open. if it's open just reload content not whole modal
        //also this allows you to nest buttons inside of modals to reload the content it is in
        //the if else are intentionally separated instead of put into a function to get the
        //button since it is using a class not an #id so there are many of them and we need
        //to ensure we get the right button and content.
        var query = "\"" + $("#search_box").val()+"\"";

        $.ajax({
            type     :'POST',
            dataType: 'jsonp',
            jsonp : 'json.wrf',
            cache    : false,
            url  : 'http://solr.kenrick95.xyz/solr/cz4034/select?q=message%3A' +  query + '&wt=json&indent=true',
            success  : function(response) {
                console.log(response.response.docs);
                var object = response.response.docs;
                var display = "";
                for(var i = 0 ; i < object.length; i++){
                    display += object[i].message;
                    display += "<br><hr>";
                }

                //var display = jQuery.parseJSON(response);
                //console.log(display);
                $('#list_data').html(display);
            }
        });

    });
</script>