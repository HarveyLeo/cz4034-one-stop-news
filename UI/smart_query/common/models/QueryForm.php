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

    public $sort;

    /**
     * @inheritdoc
     */
    public function rules()
    {
        return [
        ];
    }


}
