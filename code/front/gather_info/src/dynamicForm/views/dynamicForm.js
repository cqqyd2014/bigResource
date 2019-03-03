import React,{Component} from 'react';
import {connect} from 'react-redux';
import PropTypes from 'prop-types';
import * as Actions from '../redux/actions';
import {Form,Row,Col,Button,FormGroup,Label,Input} from 'reactstrap';
import math from '../../func/math';
import axios_ajax from '../../func/axios_ajax';
import system_info from '../../func/system_info';
import database from '../../func/database';
import axios from 'axios';
import DynamicCol from './dynamicCol';

class DynamicForm extends Component {
  constructor(props) {
    super(props);
    this.state={
        title:'',
        form_value:{},
        cols:[
            {
                id:'name',
                label:'姓名',
                input_type:'text',
                placeholder:'填写姓名',
                group_tag:'div',//fieldset,
                col_length:4
            },/* 
            {
              id:'sex',
              label:'性别',
              input_type:'radio',
              placeholder:'选择性别',
              group_tag:'fieldset',
              //fieldset,
              col_length:4,
              //legend:'选择性别',
              items:[
                {
                  text:'男',
                  value:'man'
                },
                {
                  text:'女',
                  value:'woman'
                }
                
              ]
            },
            {
              id:'favor',
              label:'爱好',
              input_type:'checkbox',
              placeholder:'勾选爱好',
              group_tag:'fieldset',
              //legend:'勾选爱好',
              items:[
                {
                  text:'足球',
                  value:'football'
                },
                {
                  text:'跑步',
                  value:'run'
                }
              ],//fieldset,
              col_length:4
            },
            {
              id:'type',
              label:'列表',
              input_type:'select',
              group_tag:'div',
              multiple:true,
              items:[
                {
                  text:'足球',
                  value:'footbal'
                },
                {
                  text:'跑步',
                  value:'run'
                }
              ],//fieldset,
              col_length:4

            },
            {
              id:'type',
              label:'列表2',
              input_type:'select',
              group_tag:'div',
              multiple:false,
              items:[
                {
                  text:'足球',
                  value:'footbal'
                },
                {
                  text:'跑步',
                  value:'run'
                }
              ],//fieldset,
              col_length:4

            } *//* ,
            {
              id:'password',
              label:'密码',
              input_type:'password',
              group_tag:'div'
            },
            {
              id:'email',
              label:'电子邮件',
              input_type:'email',
              group_tag:'div'
            },
            {
              id:'date',
              label:'日期',
              input_type:'date',
              group_tag:'div'
            },
            {
              id:"datetime",
              label:'日期时间',
              input_type:'time',
              group_tag:'div'
            } */
            
        ]
      };

  }
  
  handelSubmit=(event)=>{
    event.preventDefault();
    console.log(this.props.dynamic_form_values);

  }
  /* handleComUSCCChange=(event)=>{
    //console.log(event.target.value);
    this.props.onComUSCCChange(event.target.value);
  }
  handleComNameChange=(event)=>{
    this.props.onComNameChange(event.target.value);
  }
  handleTextChange=(event)=>{
    let o=event.target;
    let target_value={[o.name]:o.value}
    //let target_value={[o.name]:'f'}
    let old=this.state.form_value;
    let new_value=Object.assign({},old,target_value)
    console.log(new_value);
    this.setState({form_value:new_value});
  }
  getTextState=(id)=>{
    let form_value=this.state.form_value;
    let value=form_value[id];
    return value;

  }
  handleInvestAmountChange=(event)=>{
    let amount=event.target.value;
    this.setState({
      invest_amount:amount,
      invest_amount_big:math.rmbToBig(amount),
      invest_amount_thousand:math.moneyToThousand(amount)
    });
    
  } */
  componentDidMount=()=>{
    //console.log(system_info.restful_api_base_url());
    /* axios_ajax.get(system_info.restful_api_base_url(),'api/baseparameter/money_type',{},this,(a,b)=>{
      console.log(a);
      console.log(b);
    }); */
  /*   axios.get(system_info.restful_api_base_url()+'api/baseparameter/money_type')
  .then((response)=> {
    let data=database.baseparameter(response);
    //console.log(data);
    this.setState({invest_money_types:data});

  })
  .catch(function (error) {
    console.log(error);
  }); */

  }
  
  render() {
    return (
        <div>
            <h1>{this.state.title}</h1>
      <Form>
        <Row form>
        {
            this.state.cols.map((item,key)=>{
              
              

                return (
                    <DynamicCol key={key} item={item}/>
                )
            })
            

            
        }
          
        </Row>
        <Row form>
          <Button color="primary" onClick={this.handelSubmit}>确认添加</Button>
        </Row>
      </Form>
      </div>
    );
  }
  

}
DynamicForm.propTypes = {
  dynamic_form_values:PropTypes.object,
  //onComNameChange:PropTypes.func.isRequired,
  }

const mapStateToProps = (state) => {
  return {
    dynamic_form_values: state.dynamicForm.dynamic_form_values
  };
}

const mapDispatchToProps = {
  /* 
    onComUSCCChange:Actions.comUSCCChangeAction,
    onComNameChange:Actions.comNameChangeAction 
   */
};
export default connect(mapStateToProps, mapDispatchToProps)(DynamicForm);
