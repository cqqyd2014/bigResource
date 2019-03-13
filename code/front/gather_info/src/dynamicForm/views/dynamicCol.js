import React,{Component} from 'react';
import {connect} from 'react-redux';
import PropTypes from 'prop-types';
import * as Actions from '../redux/actions';
import {Col,FormGroup,Label,Input,Button} from 'reactstrap';
//import math from '../../func/math';
import system_info from '../../func/system_info';
import database from '../../func/database';
import axios from 'axios';

class DynamicCol extends Component {
  constructor(props) {
    super(props);
    let item=this.props.item;
    let col_name=item.id;
    let init_value=item.default_value;
    /* if (Object.is(item.input_type,'text')||Object.is(item.input_type,'password')||Object.is(item.input_type,'email')||Object.is(item.input_type,'date')||Object.is(item.input_type,'datetime')||Object.is(item.input_type,'time')){
      init_value='';
    }
    if ((Object.is(item.input_type,'radio')||Object.is(item.input_type,'checkbox'))){
      init_value=item.default_value;

    }
    if ((Object.is(item.input_type,'number'))){
      init_value=0;

    } */
    //let checked_value=item.default_value;
    //console.log(checked_value);
    let input_type=item.input_type
    let manual=item.manual
    let data_url=item.data_url
    let items=item.items
    //
    this.state={
        col_name:col_name,
        col_value:init_value,
        //checked_value:checked_value,
        input_type:input_type,
        manual:manual,
        data_url:data_url,
        items:items
      };

  }
  
  handleTextChange=(event)=>{
    let o=event.target;
    
    this.setState(Object.assign({},this.state,{col_value:o.value}));
    this.props.onColValueChange(this.state.col_name,o.value)
  }
  handleCheckChange=(event)=>{
    let o=event.target;
    
    let old_array=this.state.col_value;
    
    if (old_array.indexOf(o.name)>=0){
      
      old_array.splice(old_array.findIndex(item => item ===o.name ), 1)
      
    }
    else{
      old_array.push(o.name);
      
      
    }
    let new_array=old_array;
    //console.log(new_array);
    this.setState(Object.assign({},this.state,{col_value:new_array}));
    //this.setState(Object.assign({},this.state,{col_value:new_array}));
    this.props.onColValueChange(this.state.col_name,new_array);
  }
  handleRadioChange=(event)=>{
    let o=event.target;
    //console.log(this.state);
    this.setState(Object.assign({},this.state,{col_value:o.name}));
    //this.setState(Object.assign({},this.state,{col_value:o.name}));
    this.props.onColValueChange(this.state.col_name,o.name);
  }
  handleSelectMultipleClick=(event)=>{
    let o=event.target;
    let old_array=this.state.col_value;
    console.log(old_array);
    if (old_array.indexOf(o.value)>=0){
      //删除制定项目
      old_array.splice(old_array.findIndex(item => item ===o.value ), 1)
      
    }
    else{
      old_array.push(o.value);
      
      
    }
    let new_array=old_array;
    console.log(new_array);
    this.setState(Object.assign({},this.state,{col_value:new_array}));
    //this.setState(Object.assign({},this.state,{col_value:new_array}));
    this.props.onColValueChange(this.state.col_name,new_array);
  }
  handleSelectClick=(event)=>{
    let o=event.target;
    this.setState(Object.assign({},this.state,{col_value:o.value}));
    //this.setState(Object.assign({},this.state,{col_value:o.name}));
    this.props.onColValueChange(this.state.col_name,o.value);
  }
  handelCleanList=(event)=>{
    this.setState(Object.assign({},this.state,{col_value:[]}));
    //this.setState(Object.assign({},this.state,{col_value:o.name}));
    this.props.onColValueChange(this.state.col_name,[]);
  }
 
  componentWillMount=()=>{
    
    

  }
  componentDidMount=()=>{
    
    this.props.onColValueChange(this.state.col_name,this.state.col_value);
    
    //是否自动获取数据
    if (Object.is(this.state.input_type,'radio')||Object.is(this.state.input_type,'checkbox')||Object.is(this.state.input_type,'select')){
      //console.log(this.state.manual)  
      if (!this.state.manual){
          //console.log(this.state.manual)
          axios.get(system_info.restful_api_base_url()+this.state.data_url)
            .then((response)=> {
              let data=database.baseparameter(response);
              //console.log(data);
              this.setState(Object.assign({},this.state,{items:data}));
            })
            .catch(function (error) {
              console.log(error);
          });
        }
    }

  }
  
  render() {
      let item=this.props.item;
      
     
    return (
                <Col md={item.col_length} >
                        <FormGroup tag={item.group_tag}>
                          {Object.is(item.input_type,'text')||Object.is(item.input_type,'password')||Object.is(item.input_type,'email')||Object.is(item.input_type,'date')||Object.is(item.input_type,'datetime')||Object.is(item.input_type,'time')||Object.is(item.input_type,'number')?
                            (<div><Label for={item.id} >{item.label}</Label><Input value={this.state.col_value} id={item.id} name={item.id} type={item.input_type} placeholder={item.placeholder} onChange={this.handleTextChange}/></div>):
                            (Object.is(item.input_type,'radio'))?
                              (<div><legend className="col-form-label">{item.label}</legend>{this.state.items.map((radio_item,key)=>{
                                return(
                                <FormGroup check key={key}>
                                  <Label check>
                                    <Input type={item.input_type} name={radio_item.value} checked={Object.is(radio_item.value,this.state.col_value)} onChange={this.handleRadioChange}/>{radio_item.text}
                                  </Label>
                                </FormGroup>
                                )
                              })}</div>):
                              Object.is(item.input_type,'select')?item.multiple?
                              (<div><Label for={item.id} >{item.label}</Label>
                                <Input type="select" name={item.id} multiple value={this.state.col_value} onChange={this.handleSelectMultipleClick}>{this.state.items.map((select_item,key)=>{
                                return(
                                <option  key={key} value={select_item.value}>
                                  {select_item.text}
                                </option>
                                )
                              })}</Input><Button color="link" onClick={this.handelCleanList}>清空列表</Button></div>):
                              (<div><Label for={item.id} >{item.label}</Label>
                                <Input type="select" name={item.id} value={this.state.col_value}  onChange={this.handleSelectClick}>{this.state.items.map((select_item,key)=>{
                                return(
                                <option  key={key} value={select_item.value}>
                                  {select_item.text}
                                </option>
                                )
                              })}</Input></div>):
                              (Object.is(item.input_type,'checkbox'))?
                              (<div><legend className="col-form-label">{item.label}</legend>{this.state.items.map((radio_item,key)=>{
                                return(
                                <FormGroup check key={key}>
                                  <Label check>
                                    <Input type={item.input_type} name={radio_item.value} checked={(this.state.col_value.indexOf(radio_item.value)>=0)} onChange={this.handleCheckChange}/>{radio_item.text}
                                  </Label>
                                </FormGroup>
                                )
                              })}</div>):'c'}
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
