import React, { Component } from "react";
import Calendrier from "./components/calendrier";

class App extends Component {
  constructor(props) {
    super(props);
    this.state = {
      error: null,
      isLoaded: false,
      uvs: [],
      url: "https://cors-anywhere.herokuapp.com/https://webapplis.utc.fr/Edt_ent_rest/myedt/result/?login="
    };
  }
  componentDidMount() {
    
    fetch(this.state.url + this.props.val)
      .then((res) => res.json())
      .then(
        (result) => {
          this.setState({
            isLoaded: true,
            uvs: result,
          });
        },

        (error) => {
          this.setState({
            isLoaded: true,
            error,
          });
        }
      );
  }
  render() {
    const { error, isLoaded } = this.state;
    if (error) {
      return <div>Erreur : {error.message}</div>;
    } else if (!isLoaded) {
      return <div>Chargement…</div>;
    } else {
      return <Calendrier uvs={this.state.uvs} />;
    }
  }
}

export default App;
