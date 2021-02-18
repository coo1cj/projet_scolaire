import React, { Component } from "react";
import Uv from "./uv";
import { TIME } from "./heure";
class Tranche extends Component {
  state = {};
  getTranche = (day, time) => {
    if (time.includes(":30"))
      return (
        <div
          className="card  border-top-0"
          style={{ height: "2rem" }}
          key={day + time}
        >
          <Uv
            day={day}
            time={time}
            uvs={this.props.uvs}
            deleteUV={this.props.deleteUV}
          />
        </div>
      );
    return (
      <div
        className="card"
        style={{ borderBottomStyle: "dotted", height: "2rem" }}
        key={time}
      >
        <Uv
          day={day}
          time={time}
          uvs={this.props.uvs}
          deleteUV={this.props.deleteUV}
        />
      </div>
    );
  };

  render() {
    return (
      <div>{TIME.map((time) => this.getTranche(this.props.day, time))}</div>
    );
  }
}

export default Tranche;
