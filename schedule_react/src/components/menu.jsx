import React, { Component } from "react";
import { AppBar, Toolbar, Typography, Button } from "@material-ui/core";
import DropdownButton from "react-bootstrap/DropdownButton";
import Dropdown from "react-bootstrap/Dropdown";
class Menubar extends Component {
  state = {};
  showUvs = () => {
    if (this.props.uvs_sup.length === 0)
      return <Dropdown.Item className="font-weight-bold">Vide !</Dropdown.Item>;
    return this.props.uvs_sup.map((uv) => (
      <Dropdown.Item
        className="font-weight-bold"
        onClick={() => {
          this.props.addUV(uv);
        }}
        key={uv.uv + uv.type}
      >
        {uv.uv +"-"+ uv.type}
      </Dropdown.Item>
    ));
  };
  render() {
    return (
      <div style={{ flexGrow: 1 }}>
        <AppBar position="static">
          <Toolbar>
            <DropdownButton
              id="dropdown-basic-button"
              title="Ajouter une UV"
              variant="info"
            >
              {this.showUvs()}
            </DropdownButton>

            <Typography variant="h6" style={{flexGrow: 1}}/>
            <Button
              color="inherit"
              onClick={() => {
                window.location.href = "/";
              }}
            >
              Logout
            </Button>
          </Toolbar>
        </AppBar>
      </div>
    );
  }
}

export default Menubar;
