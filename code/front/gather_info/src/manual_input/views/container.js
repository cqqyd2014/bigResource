import React, {  Component} from 'react';
import ManualInputForm from './manual_input_form';





class Container extends Component {
  constructor(props) {
    super(props);


  }
  submit=(values)=>{
    console.log(values);
  }
  render() {
    return (
      <ManualInputForm onSubmit={this.submit}/>
    
    );
  }
  

}


export default Container;