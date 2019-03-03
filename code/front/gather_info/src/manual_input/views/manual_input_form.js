import React,{Component} from 'react';
import {connect} from 'react-redux';
import PropTypes from 'prop-types';
import * as Actions from '../redux/actions';
import {Form,Row,Col,Button,FormGroup,Label,Input} from 'reactstrap';
import math from '../../func/math';
import system_info from '../../func/system_info';
import database from '../../func/database';
import axios from 'axios';

class ManualInputForm extends Component {
  constructor(props) {
    super(props);
    this.state={
        invest:true,
        pull_back:false,
        invest_amount:0,
        invest_amount_big:'',
        invest_amount_thousand:0,
        invest_money_type:'RMB',
        invest_money_types:[]
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
  handleInvestChange=(event)=>{
    let invest=!this.state.invest;
    let pull_back=!this.state.pull_back;
    this.setState({invest:invest,pull_back:pull_back});
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
      <Form>
        <Row form>
          <Col md={4} >
            <FormGroup>
              <Label for="comUSCC" >统一社会信用代码</Label>
              <Input id="comUSCC" value={this.state.comUSCC} type="text" onChange={this.handleComUSCCChange} placeholder="输入单位的统一社会信用代码" />
            </FormGroup>
          </Col>
          <Col md={4} >
            <FormGroup>
              <Label for="comName" >公司名称</Label>
              <Input id="comName" type="text" value={this.state.comName} onChange={this.handleComNameChange} placeholder="输入单位的名称"/>
            </FormGroup>
          </Col>
          <Col md={4}>
            <FormGroup tag="fieldset">
              <legend>投资类型</legend>
              <FormGroup check inline>
                <Label check>
                <Input type="radio" name="invest"  checked={this.state.invest} onChange={this.handleInvestChange}/>
                  投资
                </Label>
              </FormGroup>
              <FormGroup check inline>
                <Label check>
                <Input type="radio" name="full_back" checked={this.state.pull_back} onChange={this.handleInvestChange}/>
                  撤资
                </Label>
              </FormGroup>
            </FormGroup>
          </Col>
          <Col md={4}>
            <FormGroup>
              <Label for="investDate">投资日期</Label>
              <Input id="investDate" type="date" placeholder="投资日期"/>
            </FormGroup>
          </Col>
          <Col md={4}>
            <FormGroup>
              <Label for="investMoneyType">币种</Label>
              <Input type="select" name="investMoneyType" id="investMoneyType">
              {
                this.state.invest_money_types.map(function(item,key,ary) {
                  return (<option key={item.key} value={item.key}>{item.value}</option>);
                })
              }
              </Input>
            </FormGroup>
          </Col>
        </Row>
        <Row form>
          <Col md={4}>
            <FormGroup>
              <Label for="investAmount">投资金额</Label>
              <Input id="investAmount" type="number" placeholder="单位元" value={this.state.invest_amount} onChange={this.handleInvestAmountChange}/>
            </FormGroup>
          </Col>
          <Col md={4}>
            <FormGroup>
              <Label for="investAmount">投资金额大写</Label>
              <Input id="investAmount" type="type" value={this.state.invest_amount_big} readOnly={true} placeholder="单位元"/>
            </FormGroup>
          </Col>
          <Col md={4}>
            <FormGroup>
              <Label for="investAmount">投资金额千分号</Label>
              <Input id="investAmount" type="type" value={this.state.invest_amount_thousand} readOnly={true} placeholder="单位元"/>
            </FormGroup>
          </Col>
          
        </Row>
        <Row form>
          <Button color="primary" onClick={this.handelSubmit}>确认添加</Button>
        </Row>
      </Form>
    );
  }
  

}
ManualInputForm.propTypes = {
  onComUSCCChange:PropTypes.func.isRequired,
  onComNameChange:PropTypes.func.isRequired,
  }

const mapStateToProps = (state) => {
  return {
    //todos: selectVisibleTodos(state.todos, state.filter)
  };
}

const mapDispatchToProps = {
  
    onComUSCCChange:Actions.comUSCCChangeAction,
    onComNameChange:Actions.comNameChangeAction 
  
};
export default connect(mapStateToProps, mapDispatchToProps)(ManualInputForm);
