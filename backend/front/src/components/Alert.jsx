import React from "react";
import {Button, Modal} from "react-bootstrap";
// Используется для генерации Alert - сообщений
const Alert = (props) => {
    const {message, title, ok, close, cancelButton, modal} = props;

    const onok = () => {
        close();
        ok && ok();
    }

    return (
        <Modal show={modal}>
            <Modal.Header>{title}</Modal.Header>
            <Modal.Body>
                {message}
            </Modal.Body>

            <Modal.Footer>
                <Button onClick={onok} className="btn btn-primary mr-2">OK</Button>
                {cancelButton &&
                <Button onClick={close} className="btn btn-secondary">CANCEL</Button>}
            </Modal.Footer>
        </Modal>
    );
}

export default Alert;