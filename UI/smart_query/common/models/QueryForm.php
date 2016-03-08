<?php
namespace common\models;

use Yii;
use yii\base\Model;

/**
 * Login form
 */
class QueryForm extends Model
{
    public $category;

    public $sentiment_analysis;

    /**
     * @inheritdoc
     */
    public function rules()
    {
        return [
        ];
    }


}
