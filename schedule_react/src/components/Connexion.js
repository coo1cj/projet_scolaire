import React, { Component, Fragment } from 'react'
import App from '../app'

class Connexion extends Component {
    constructor(props){
        super(props)
        this.state = {
            user : "",
            input : ""
        }
        this.handleUser = this.handleUser.bind(this)
        this.handleChange = this.handleChange.bind(this)
    }
    handleUser(){
        this.setState({user : this.state.input}) 
    }
    handleChange(event){
        this.setState({input : event.target.value })
    }

    render() {
        if(this.state.user === "")
            return (
                    <nav className="navbar navbar-light bg-light">
                    <form className="form-inline" >
                        <div className="input-group">
                        <div className="input-group-prepend">
                            <span className="input-group-text" id="basic-addon1">@</span>
                        </div>
                        <input type="text" className="form-control" placeholder="Username" aria-label="Username" aria-describedby="basic-addon1" onChange={this.handleChange}/>
                        </div>
                        <button className="btn btn-outline-success" onClick={this.handleUser}>Search</button>
                    </form>
                    </nav>
            )
        return (<App val={this.state.user}/>)
    }
}

export default Connexion