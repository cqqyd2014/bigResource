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

class DefinitionForm extends Component {
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
                group_tag:'div'//fieldset
            },
            {
              id:'sex',
              label:'性别',
              input_type:'radio',
              placeholder:'选择性别',
              group_tag:'fieldset',
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
              ]
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
              ]

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
              ]

            }/* ,
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

  }
  handleComUSCCChange=(event)=>{
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
    
  }
  componentDidMount=()=>{
    //console.log(system_info.restful_api_base_url());
    /* axios_ajax.get(system_info.restful_api_base_url(),'api/baseparameter/money_type',{},this,(a,b)=>{
      console.log(a);
      console.log(b);
    }); */
    axios.get(system_info.restful_api_base_url()+'api/baseparameter/money_type')
  .then((response)=> {
    let data=database.baseparameter(response);
    //console.log(data);
    this.setState({invest_money_types:data});

  })
  .catch(function (error) {
    console.log(error);
  });

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
                    <Col md={4} key={key}>
                        <FormGroup tag={item.group_tag}>
                          {Object.is(item.input_type,'text')||Object.is(item.input_type,'password')||Object.is(item.input_type,'email')||Object.is(item.input_type,'date')||Object.is(item.input_type,'datetime')||Object.is(item.input_type,'time')||Object.is(item.input_type,'number')?
                            (<div><Label for={item.id} >{item.label}</Label><Input value={this.getTextState(`${item.id}`)} id={item.id} name={item.id} type={item.input_type} placeholder={item.placeholder} onChange={this.handleTextChange}/></div>):
                            (Object.is(item.input_type,'radio')||Object.is(item.input_type,'checkbox'))?
                              (<div><legend className="col-form-label">{item.label}</legend>{item.items.map((radio_item,key)=>{
                                return(
                                <FormGroup check key={key}>
                                  <Label check>
                                    <Input type={item.input_type} name={radio_item.value} />{radio_item.text}
                                  </Label>
                                </FormGroup>
                                )
                              })}</div>):
                              Object.is(item.input_type,'select')?item.multiple?
                              (<div><Label for={item.id} >{item.label}</Label>
                                <Input type="select" name={item.id} multiple>{item.items.map((select_item,key)=>{
                                return(
                                <option  key={key} value={select_item.value}>
                                  {select_item.text}
                                </option>
                                )
                              })}</Input></div>):
                              (<div><Label for={item.id} >{item.label}</Label>
                                <Input type="select" name={item.id} >{item.items.map((select_item,key)=>{
                                return(
                                <option  key={key} value={select_item.value}>
                                  {select_item.text}
                                </option>
                                )
                              })}</Input></div>):
                              'c'}
                        </FormGroup>
                    </Col>
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
DefinitionForm.propTypes = {
  //onComUSCCChange:PropTypes.func.isRequired,
  //onComNameChange:PropTypes.func.isRequired,
  }

const mapStateToProps = (state) => {
  return {
    //todos: selectVisibleTodos(state.todos, state.filter)
  };
}

const mapDispatchToProps = {
  /* 
    onComUSCCChange:Actions.comUSCCChangeAction,
    onComNameChange:Actions.comNameChangeAction 
   */
};
export default connect(mapStateToProps, mapDispatchToProps)(DefinitionForm);
