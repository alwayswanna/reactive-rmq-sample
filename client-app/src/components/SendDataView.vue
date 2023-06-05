<script setup>
</script>

<template>
    <div><input type="text" placeholder="User message" v-model="message"/></div>
    <div><input type="text" placeholder="User" v-model="username"/></div>
    <div>
        <button v-on:click="sendData()">Send data</button>
    </div>
</template>

<script>
import axios from 'axios';

export default {
    name: "App",
    data() {
        return {
            message: '',
            username: ''
        }
    },
    methods: {
        sendData() {
            axios.post("http://localhost:7001/api/v1/data/message", {
                username: this.username,
                message: this.message
            })
            this.message = ''
            this.username = ''
        }
    }
}
</script>

<style>
input {
    background-color: #181818;
    font-size: 200%;
    padding: 10px;
    margin: 5px;
    border-color: #00bd7e;
    border-radius: 15px;
    color: whitesmoke;
}

button {
    background-color: #00bd7e;
    padding: 20px;
    font-size: 200%;
    margin: 5px;
    border-radius: 15px;
    border-color: black;
    color: whitesmoke;
}
</style>
