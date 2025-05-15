import React from "react";
import './App.css';

// Modern React recommends using functional React instead
class App extends React.Component {
  
    // Constructor
    constructor(props) {
        super(props);
  
        this.state = {
            items: [],
            DetailsLoaded: false
        };
    }
  
    // ComponentDidMount is used to
    // execute the code
    componentDidMount() {
        fetch("http://localhost:8080/api/v1/user/hello")
            .then((res) => res.json())
            .then((json) => {
                this.setState({
                    items: json,
                    DetailsLoaded: true
                });
            })
    }
    render() {
        const { DetailsLoaded, items } = this.state;
        if (!DetailsLoaded) return <div><h1> Please wait ... </h1> </div> ;
  
        return (
        <div className = "App">
            <h1> Fetch data from an api in react </h1>  
            <ol key = { items.id } >
                Full Name: { items.name }
            </ol>
        </div>
    );
}
}
  
export default App;
