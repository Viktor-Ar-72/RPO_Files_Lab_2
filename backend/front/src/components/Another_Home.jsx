import React from 'react';
import 'bootstrap/dist/css/bootstrap-grid.min.css'
import './1.css'

class Another_Home extends React.Component {
    constructor(props) {
        super(props);
    }

    render() {
        return (
            <div className="mt-5 me-auto">
                <h2><mark_1>Artistic Frontend</mark_1></h2>
                <p><mark_2>
                    Вторая страница
                </mark_2>
                </p>
            </div>
        );
    }
}
export default Another_Home;