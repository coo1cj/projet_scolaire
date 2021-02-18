import React, { Component, Fragment } from "react";
import { confirmAlert } from "react-confirm-alert";
import "react-confirm-alert/src/react-confirm-alert.css";
import { WEEK } from "./titre";
const TYPE = ["primary", "secondary", "success", "danger", "warning", "info"];
class Uv extends Component {
  findMyUv = (uvs, day, time) => {
    let myUv = "";
    let i = 0;
    for (i = 0; i < uvs.length; i++) {
      if (uvs[i].day === day.toUpperCase()) {
        if (
          time.replace(":00", ":15") === uvs[i].begin ||
          uvs[i].begin === time
        )
          myUv = uvs[i];
      }
    }
    return myUv;
  };

  getHue = (uv) => {
    let sum = 0;
    for(let i = 0; i < uv.length; ++i) {
      sum += uv.charCodeAt(i);
      sum *= uv.charCodeAt(i);
    }
    sum = sum % 360.;
    return sum;
  }

  makeStyle = (myUv) => {
    let myStyle = { width: "10.85rem" };
    let hour =
      parseInt(myUv.end.substr(0, 2)) - parseInt(myUv.begin.substr(0, 2));
    hour = hour * 4;
    myStyle["height"] = hour.toString() + "rem";
    myStyle["background-color"] = "hsl(" + this.getHue(myUv.uv) + ", 85%, 50%)";
    if (myUv.begin.includes(":15")) myStyle["marginTop"] = "1rem";
    return myStyle;
  };

  maketodaybg = (day, time) => {
    if (WEEK[new Date().getDay()] === day && time === "08:00") {
      return (
        <div
          style={{ zIndex: "5", position: "relative" }}
          key={day + time + "bg"}
        >
          <div
            className="card border-O bg-info"
            style={{ height: "48rem", opacity: ".1" }}
          ></div>
        </div>
      );
    }
  };

  handleDelete = (myUv) => {
    confirmAlert({
      title: "Supprimer l'UV",
      message: "Voulez vous vraiment supprimer cette UV ?",
      buttons: [
        {
          label: "Oui",
          onClick: () => {
            this.props.deleteUV(myUv);
          },
        },
        {
          label: "Non",
        },
      ],
    });
  };

  render() {
    let myUv = "";
    let i = 0;
    for (i = 0; i < this.props.uvs.length; i++) {
      if (this.props.uvs[i].day === this.props.day.toUpperCase()) {
        if (
          this.props.time.replace(":00", ":15") === this.props.uvs[i].begin ||
          this.props.uvs[i].begin === this.props.time
        )
          myUv = this.props.uvs[i];
      }
    }
    if (myUv === "")
      return (
        <Fragment>{this.maketodaybg(this.props.day, this.props.time)}</Fragment>
      );
    return (
      <Fragment>
        <div
          style={{ zIndex: "10" }}
          key={this.props.day + this.props.time + myUv.uv}
          onClick={() => this.handleDelete(myUv)}
        >
          <div
            className={"position-absolute card"}
            style={this.makeStyle(myUv)}
          >
            <h5 className="text-xl-center " onClick={this.handleDelete}>
              {myUv.uv}-{myUv.type}
            </h5>
            <p className="text-sm-center ">
              {myUv.group}-{myUv.room}
            </p>
          </div>
        </div>
        {this.maketodaybg(this.props.day, this.props.time)}
      </Fragment>
    );
  }
}

export default Uv;
