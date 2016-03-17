<?php

    use kartik\select2\Select2;
    use kartik\widgets\ActiveForm;
    use yii\widgets\ListView;
    use yii\helpers\Html;/* @var $this yii\web\View */
    $data = ['Strait times', 'BBC', 'Reuters' , 'Guardians', 'CNN'];
    $this->title = 'Smart Query';

?>
<?php $form = ActiveForm::begin(['action' => ['site/index'], 'method' => 'post', 'id' => 'query_form']) ?>

<div class="site-index">
    <div class="row">
        <div class="col-md-6">
            <?= $form->field($query_form, 'category')->radioButtonGroup($data,[
                'class' => 'btn-group-lg',
                'itemOptions' => ['labelOptions' => ['class' => 'btn btn-warning']]
            ])->label(false) ?>
        </div>

        <div class="col-md-4">
            <?= Select2::widget([
                'name' => 'sort_by',
                'id' => 'sort_by',
                'hideSearch' => true,
                'data' => ['Popularity' => 'Popularity', 'Latest' => 'Latest'],
                'options' => ['placeholder' => 'Sorted By'],
                'pluginOptions' => [
                    'allowClear' => true
                ],
            ]) ?>
        </div>

        <?= Html::hiddenInput('data', null, ['id' => 'hi_data']) ?>

    </div>

    <div class="col-md-7">
        <div class="row">
                <div class="col-md-8">
                </div>
        </div>

            <hr>
        <div class="row" align="center">
            <label>Query Result</label>
        </div>

        <br>

        <?php if(isset($has_data)){ ?>
            <div class="row" id="list_data">
                <?= ListView::widget([
                    'id' => 'threadList',
                    'dataProvider' => $data_provider,
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

        <?php } ?>
        <?php $form = ActiveForm::end() ?>


    </div>
</div>
