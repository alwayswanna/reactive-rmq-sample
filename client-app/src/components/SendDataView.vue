<script setup>
</script>

<template>
    <div><input class="text-input" type="text" placeholder="User message" v-model="message"/></div>
    <div><input class="text-input" type="text" placeholder="User" v-model="username"/></div>
    <div>
        <button v-on:click="sendData()">Send data</button>
    </div>

    <br>

    <div class="choose-file">
        <input class="file" type="file" placeholder="Choose .xlsx file" @change="handleFileUpload( $event )" ref="file"/>
    </div>
    <div>
        <button v-on:click="sendFile()">Send file</button>
    </div>
</template>

<script>
import axios from 'axios';

export default {
    name: "App",
    data() {
        return {
            message: '',
            username: '',
            file: ''
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
        },
        handleFileUpload() {
            this.file = this.$refs.file.files[0];
        },
        sendFile() {
            console.log(this.file)
            let formData = new FormData()
            formData.append("file", this.file)
            console.log("file", this.file)
            axios.post("http://localhost:7001/api/v1/data/file", formData).then(response => console.log(response.status))
            this.$refs.file.files[0] = null
        }
    }
}
</script>

<style>
.text-input {
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

.file {
    opacity: 100;
}

.choose-file{
    margin: 5px;
    padding: 10px;
    background-color: #00bd7e;
    border-radius: 15px;
    color:#fff;
    cursor:pointer
}
</style>
