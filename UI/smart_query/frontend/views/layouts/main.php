<?php

/* @var $this \yii\web\View */
/* @var $content string */

use yii\helpers\Html;
use yii\bootstrap\Nav;
use yii\bootstrap\NavBar;
use yii\widgets\Breadcrumbs;
use frontend\assets\AppAsset;
use common\widgets\Alert;
use kartik\select2\Select2;
AppAsset::register($this);
?>
<?php $this->beginPage() ?>
<!DOCTYPE html>
<html lang="<?= Yii::$app->language ?>">
<head>
    <meta charset="<?= Yii::$app->charset ?>">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <?= Html::csrfMetaTags() ?>
    <title><?= Html::encode($this->title) ?></title>
    <?php $this->head() ?>
</head>
<body>
<?php

$this->beginBody() ?>

<div class="wrap">
    <?php
    NavBar::begin([
        'brandLabel' => 'Smart Query',
        'brandUrl' => Yii::$app->homeUrl,
        'options' => [
            'class' => 'navbar-inverse navbar-fixed-top mynavbar',
        ],
    ]) ?>

    <div class="collapse navbar-collapse">

        <ul class="nav navbar-nav mynav">
              <li class="item">
                  <?=  Html::textInput(
                       'search_box_menu',
                        null,
                        ['placeholder' => 'Search', 'class' => 'form-control','id' => 'search_box']

                    )
                 ?>
           </li>
        </ul>
        <?= Html::button('Search' , ['class' => 'btn btn-primary', 'style' => 'margin-top:5px', 'id' => 'query_button']) ?>
    </div>
<?php
    NavBar::end();
    ?>

    <div class="container">
        <?= Breadcrumbs::widget([
            'links' => isset($this->params['breadcrumbs']) ? $this->params['breadcrumbs'] : [],
        ]) ?>
        <?= Alert::widget() ?>
        <?= $content ?>
    </div>
</div>

<footer class="footer">
    <div class="container">
        <p class="pull-left">&copy; My Company <?= date('Y') ?></p>

        <p class="pull-right"><?= Yii::powered() ?></p>
    </div>
</footer>
<?php
$this->registerJsFile(Yii::$app->request->baseUrl . '/frontend/web/js/jquery.js');
$this->registerJsFile(Yii::$app->request->baseUrl . '/frontend/web/js/script.js') ?>

<?php $this->endBody() ?>
</body>
</html>
<?php $this->endPage() ?>
