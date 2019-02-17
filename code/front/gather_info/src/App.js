"user stirct"
import React,{Component} from 'react';
import {view} from './manual_input';

const ManualInput=view;

class App extends Component {
  
  render() {
    return (
      <div>
        <ManualInput/>
        
      </div>
    );
  }
}

export default App;