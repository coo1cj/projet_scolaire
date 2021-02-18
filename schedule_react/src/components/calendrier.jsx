import React, { Component } from "react";
import Tranche from "./tranche";
import Titre from "./titre";
import Heure from "./heure";
import { WEEK } from "./titre";
import Menubar from "./menu";
class Calendrier extends Component {
  constructor(props) {
    super(props);
    this.state = {
      uvs_sup: [],
      uvs_rest: this.props.uvs,
    };
  }
  deleteUV = (uvsp) => {
    let temp_sup = this.state.uvs_sup;
    temp_sup.push(uvsp);
    let temp_rest = this.state.uvs_rest;
    temp_rest = temp_rest.filter((uv) => uv !== uvsp);
    this.setState({ uvs_sup: temp_sup });
    this.setState({ uvs_rest: temp_rest });
  };
  addUV = (uv) => {
    let temp_rest = this.state.uvs_rest;
    temp_rest.push(uv);
    let temp_sup = this.state.uvs_sup;
    temp_sup = temp_sup.filter((uvsp) => uvsp !== uv);
    this.setState({ uvs_sup: temp_sup });
    this.setState({ uvs_rest: temp_rest });
  };
  render() {
    return (
      <div>
        <Menubar
          uvs={this.state.uvs_rest}
          uvs_sup={this.state.uvs_sup}
          uvs_rest={this.state.uvs_rest}
          addUV={this.addUV}
        />
        <Titre />
        <div className="row">
          <div className="col p-0">
            <Heure />
          </div>

          <div className="col p-0">
            <Tranche
              day={WEEK[0]}
              uvs={this.state.uvs_rest}
              deleteUV={this.deleteUV}
            />
          </div>

          <div className="col p-0">
            <Tranche
              day={WEEK[1]}
              uvs={this.state.uvs_rest}
              deleteUV={this.deleteUV}
            />
          </div>

          <div className="col p-0">
            <Tranche
              day={WEEK[2]}
              uvs={this.state.uvs_rest}
              deleteUV={this.deleteUV}
            />
          </div>

          <div className="col p-0">
            <Tranche
              day={WEEK[3]}
              uvs={this.state.uvs_rest}
              deleteUV={this.deleteUV}
            />
          </div>

          <div className="col p-0">
            <Tranche
              day={WEEK[4]}
              uvs={this.state.uvs_rest}
              deleteUV={this.deleteUV}
            />
          </div>

          <div className="col p-0">
            <Tranche
              day={WEEK[5]}
              uvs={this.state.uvs_rest}
              deleteUV={this.deleteUV}
            />
          </div>

          <div className="col p-0">
            <Tranche
              day={WEEK[6]}
              uvs={this.state.uvs_rest}
              deleteUV={this.deleteUV}
            />
          </div>
        </div>
      </div>
    );
  }
}

export default Calendrier;
