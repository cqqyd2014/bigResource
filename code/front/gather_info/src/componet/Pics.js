import React, { Component } from 'react';
import {BrowserRouter as Router,Route,Link} from 'react-router-dom';

class Pics extends Component {

    constructor(props){
        super(props);
        this.state={
          list:[{
            id:"1",
            title:"abc"
          },{
            id:"2",
            title:"def"
          }]
        }
    }



    render() {
      return (
        <div>
          图片列表
          <ul>
            {
              this.state.list.map((value,key)=>{
                
                  return (
                  <li key={key}>
                    <Link to={`/pic/${value.id}`}>{value.title}</Link>
                  </li
                  >)
                
              })
            }
          </ul>
        </div>
      );
    }
  }
  
  export default Pics;