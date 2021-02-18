import React, { Component } from "react";

export const WEEK = [
  "Dimanche",
  "Lundi",
  "Mardi",
  "Mercredi",
  "Jeudi",
  "Vendredi",
  "Samedi",
];
class Titre extends Component {
  constructor() {
    super();
    this.state = {
      day: WEEK[new Date().getDay()],
    };
  }

  render() {
    return (
      <div className="row ">
        <div className="col p-0  " key="nothing">
          <div className="card w-100 h-100" key="nothing">
            <div className="card-body" key="nothing"></div>
          </div>
        </div>
        {WEEK.map((jour) => (
          <div className="col p-0  " key={jour}>
            <div className="card w-100 h-100" key={jour}>
              <div className="card-body" key={jour}>
                <h5
                  key={jour}
                  className={
                    jour === this.state.day ? "text-primary" : "text-secondary"
                  }
                >
                  {jour}
                </h5>
              </div>
            </div>
          </div>
        ))}
      </div>
    );
  }
}

export default Titre;
