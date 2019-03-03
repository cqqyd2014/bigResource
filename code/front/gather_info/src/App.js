"user stirct"
import React,{Component} from 'react';
import {view} from './dynamicForm';

const DynamicForm=view;

class App extends Component {
  
  render() {
    return (
      <div>
        <DynamicForm/>
        
      </div>
    );
  }
}

export default App;