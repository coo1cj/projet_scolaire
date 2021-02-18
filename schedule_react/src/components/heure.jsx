import React, { Component } from "react";
export const TIME = [
  "08:00",
  "08:30",
  "09:00",
  "09:30",
  "10:00",
  "10:30",
  "11:00",
  "11:30",
  "12:00",
  "12:30",
  "13:00",
  "13:30",
  "14:00",
  "14:30",
  "15:00",
  "15:30",
  "16:00",
  "16:30",
  "17:00",
  "17:30",
  "18:00",
  "18:30",
  "19:00",
  "19:30",
];
class Heure extends Component {
  state = {};

  getHeure = (time) => {
    if (time.includes(":30"))
      return (
        <div
          className="card  border-top-0"
          style={{ height: "2rem" }}
          key={time}
        >
          <h5 className="text-xl-center text-white" key={time}>
            {time}
          </h5>
        </div>
      );
    return (
      <div
        className="card "
        style={{ borderBottomStyle: "dotted", height: "2rem" }}
        key={time}
      >
        <h5 className="text-xl-center text-secondary" key={time}>
          {time}
        </h5>
      </div>
    );
  };
  render() {
    return (
      <div>
        {TIME.map((time) => (
          <div className="col p-0  " key={time}>
            {this.getHeure(time)}
          </div>
        ))}
      </div>
    );
  }
}

export default Heure;
