import React from "react";
import {Navbar, Nav} from "react-bootstrap";
import {faHome} from "@fortawesome/free-solid-svg-icons";
import {FontAwesomeIcon} from "@fortawesome/react-fontawesome";
import {Link} from "react-router-dom";

import {useNavigate} from 'react-router-dom'

class NavigationBarClass extends React.Component {
    constructor(props) {
        super(props);
        this.goHome = this.goHome.bind(this);
    }

    goHome() {
        this.props.navigate('Another_Home');
    }

    render() {
        return (
            <Navbar bg="light" expand="lg">
                <Navbar.Brand><FontAwesomeIcon icon={faHome}/>{' '}My RPO</Navbar.Brand>
                <Navbar.Toggle aria-controls="basic-navbar-nav"/>
                <Navbar.Collapse id="basic-navbar-nav">
                    {/* Старая часть, закоменчена*

                    <Nav className = "me-auto">
                        <Nav.Link>Home</Nav.Link>
                        <Nav.Link>Link</Nav.Link>
                    </Nav>
                    */}

                    <Nav className="me-auto">
                        {/*<Link to= "/home" > Home</Link> */}
                        <Nav.Link href={"/home"}> Home </Nav.Link>
                        {/*<Nav.Link onClick={this.goHome}> Home </Nav.Link>*/}
                        <Nav.Link onClick={this.goHome}>Another Home</Nav.Link>
                    </Nav>
                </Navbar.Collapse>
            </Navbar>
        );
    }
}


const NavigationBar = props => {
    const navigate = useNavigate()
    return <NavigationBarClass navigate={navigate} {...props} />
}

//export default NavigationBarClass;
export default NavigationBar;