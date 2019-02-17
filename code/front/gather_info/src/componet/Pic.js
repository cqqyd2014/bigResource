import React, { Component } from 'react';
import {BrowserRouter as Router,Route,Link} from 'react-router-dom';

class Pic extends Component {

    constructor(props){
        super(props);
        this.state={
          
        }
    }

    componentDidMount(){
        //console.log("wangli");
        console.log(this.props.match.params.id)
    }

    render() {
      return (
        <div>
          图片
        </div>
      );
    }
  }
  
  export default Pic;