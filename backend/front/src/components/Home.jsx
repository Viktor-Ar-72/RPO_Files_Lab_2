import React from 'react';
import 'bootstrap/dist/css/bootstrap-grid.min.css'

class Home extends React.Component {
    constructor(props) {
        super(props);
    }
    render() {
        return (
            <div className="mt-5 me-auto">
                <h2>RPO Art Frontend</h2>
                <p>
                    Привет
                </p>
            </div>
        );
    }
}
export default Home;