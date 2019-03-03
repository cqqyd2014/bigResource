import React,{Component} from 'react';
import {connect} from 'react-redux';
import PropTypes from 'prop-types';
import * as Actions from '../redux/actions';
import {Col,FormGroup,Label,Input} from 'reactstrap';
import math from '../../func/math';
import axios_ajax from '../../func/axios_ajax';
import system_info from '../../func/system_info';
import database from '../../func/database';
import axios from 'axios';

class DynamicCol extends Component {
  constructor(props) {
    super(props);
    let item=this.props.item;
    let col_name=item.id;
    let init_value=Object.is(item.input_type,'text')||Object.is(item.input_type,'password')||Object.is(item.input_type,'email')||Object.is(item.input_type,'date')||Object.is(item.input_type,'datetime')||Object.is(item.input_type,'time')?'':0;
   
    //
    this.state={
        col_name:col_name,
        col_value:init_value
      };

  }
  
  /* handelSubmit=(event)=>{
    event.preventDefault();

  }
  handleComUSCCChange=(event)=>{
    //console.log(event.target.value);
    this.props.onComUSCCChange(event.target.value);
  }
  handleComNameChange=(event)=>{
    this.props.onComNameChange(event.target.value);
  } */
  handleTextChange=(event)=>{
    let o=event.target;
    
    this.setState(Object.assign({},this.state,{col_value:o.value}));
    /* console.log(this.state.col_name);
    console.log(o.value);
     */this.props.onColValueChange(this.state.col_name,o.value)
  }
 
  /* handleInvestAmountChange=(event)=>{
    let amount=event.target.value;
    this.setState({
      invest_amount:amount,
      invest_amount_big:math.rmbToBig(amount),
      invest_amount_thousand:math.moneyToThousand(amount)
    });
    
  }
   */componentWillMount=()=>{
    
    

  }
  componentDidMount=()=>{
    //console.log(this.state.col_name);
    this.props.onColValueChange(this.state.col_name,this.state.col_value);



    //console.log(system_info.restful_api_base_url());
    /* axios_ajax.get(system_info.restful_api_base_url(),'api/baseparameter/money_type',{},this,(a,b)=>{
      console.log(a);
      console.log(b);
    }); */
    /* axios.get(system_info.restful_api_base_url()+'api/baseparameter/money_type')
  .then((response)=> {
    let data=database.baseparameter(response);
    //console.log(data);
    this.setState({invest_money_types:data});

  })
  .catch(function (error) {
    console.log(error);
  });
 */
  }
  
  render() {
      let item=this.props.item;
      
     
    return (
                <Col md={item.col_length} >
                        <FormGroup tag={item.group_tag}>
                          {Object.is(item.input_type,'text')||Object.is(item.input_type,'password')||Object.is(item.input_type,'email')||Object.is(item.input_type,'date')||Object.is(item.input_type,'datetime')||Object.is(item.input_type,'time')||Object.is(item.input_type,'number')?
                            (<div><Label for={item.id} >{item.label}</Label><Input value={this.state.value} id={item.id} name={item.id} type={item.input_type} placeholder={item.placeholder} onChange={this.handleTextChange}/></div>):
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
            

            
        
          
      
    );
  }
  

}
DynamicCol.propTypes = {
  onColValueChange:PropTypes.func.isRequired,
  //onComNameChange:PropTypes.func.isRequired,
  }

const mapStateToProps = (state) => {
  return {
    //todos: selectVisibleTodos(state.todos, state.filter)
  };
}

const mapDispatchToProps = {
  
    onColValueChange:Actions.colValueChangeAction,
    //onComNameChange:Actions.comNameChangeAction 
  
};
export default connect(mapStateToProps, mapDispatchToProps)(DynamicCol);
