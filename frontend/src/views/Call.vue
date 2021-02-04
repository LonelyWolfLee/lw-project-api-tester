<template>
  <div class="api">
    <v-row>
      <v-col class="request" cols="12" sm="6">
        <h2>REQUEST</h2>
        <v-card class="my-2">
          <v-card-title>TARGET ADDRESS</v-card-title>
          <v-card-text>
            <v-overflow-btn :items="callMethods" v-model="request.method"></v-overflow-btn>
            <v-text-field outlined label="URI" v-model="request.uri" clearable></v-text-field>
          </v-card-text>
        </v-card>
        <v-card class="my-2">
          <v-card-title>HEADERS</v-card-title>
          <v-card-text>
            <v-text-field
              outlined
              label="key=value <enter>"
              v-model="insertedHeader"
              @keypress.enter.prevent="insertHeader(insertedHeader)"
              clearable></v-text-field>
            <v-simple-table>
              <template v-slot:default>
                <thead>
                <tr>
                  <th class="text-left">
                    KEY
                  </th>
                  <th class="text-left">
                    VALUE
                  </th>
                  <th class="text-left">

                  </th>
                </tr>
                </thead>
                <tbody>
                <tr
                  v-for="item in request.headers"
                  :key="item.key"
                >
                  <td>{{ item.key }}</td>
                  <td>{{ item.value }}</td>
                  <td><v-icon @click="deleteHeader(item)">mdi-delete</v-icon></td>
                </tr>
                </tbody>
              </template>
            </v-simple-table>
          </v-card-text>
        </v-card>
        <v-card class="my-2">
          <v-card-title>BODY</v-card-title>
          <v-card-text>
            <v-textarea clearable outlined v-model="request.body" auto-grow></v-textarea>
          </v-card-text>
        </v-card>
        <v-btn x-large color="primary" width="100%" @click="sendRequest">SEND</v-btn>
      </v-col>
      <v-spacer></v-spacer>
      <v-col class="response" cols="12" sm="6">
        <h2>RESPONSE</h2>
        <v-card class="my-2">
          <v-card-title>STATUS</v-card-title>
          <v-card-text>
            <v-text-field disabled outlined :value="response.status"></v-text-field>
          </v-card-text>
        </v-card>
        <v-card class="my-2">
          <v-card-title>HEADERS</v-card-title>
          <v-card-text>
            <v-simple-table disabled>
              <template v-slot:default>
                <thead>
                <tr>
                  <th class="text-left">
                    KEY
                  </th>
                  <th class="text-left">
                    VALUE
                  </th>
                </tr>
                </thead>
                <tbody>
                <tr
                  v-for="item in response.headers"
                  :key="item.key"
                >
                  <td>{{ item.key }}</td>
                  <td>{{ item.value }}</td>
                </tr>
                </tbody>
              </template>
            </v-simple-table>
          </v-card-text>
        </v-card>
        <v-card class="my-2">
          <v-card-title>BODY</v-card-title>
          <v-card-text>
            <v-textarea disabled outlined v-model="response.body" auto-grow></v-textarea>
          </v-card-text>
        </v-card>
      </v-col>
    </v-row>
  </div>
</template>

<style scoped>
.api {
  margin: 32px;
}
.request {
  outline-style: auto;
}
.response {
  outline-style: auto;
}
</style>

<script>
let axios = require('axios');
export default {
  name: 'Api',
  data: () => ({
    request: {
      method: "POST",
      uri: "",
      headers: [],
      body: ""
    },
    response: {
      status: "",
      headers: [],
      body: ""
    },
    callMethods: [
      {text: "GET"},
      {text: "POST"},
    ],
    insertedHeader: ""
  }),
  methods: {
    insertHeader(text) {
      let headerElements = text.split("=");
      if (headerElements.length === 2) {
        this.request.headers.push({key: headerElements[0], value: headerElements[1]});
        this.insertedHeader = "";
      } else {
        alert("잘못된 HEADER 형식");
      }
    },
    deleteHeader(item) {
      let idx = this.request.headers.indexOf(item);
      this.request.headers.splice(idx, 1);
    },
    sendRequest() {
      if(!this.request.uri) {
        alert("No request uri");
        return;
      }

      if(!this.request.body) {
        alert("No request body");
        return;
      }

      this.response.status = "";
      this.response.headers = [];
      this.response.body = "";

      let myResponse = this.response;
      axios.post("/api/request/call", this.request)
        .then(function (response) {
          myResponse.status = response.status + " " + response.statusText;
          Object.keys(response.headers)
              .forEach(headerKey => myResponse.headers.push({key: headerKey, value: response.headers[headerKey]}));
          myResponse.body = response.data;
        })
        .catch(function (error) {
          if (error.response) {
            myResponse.status = error.response.status + " " + error.response.statusText;
            Object.keys(error.response.headers)
                .forEach(headerKey => myResponse.headers.push({key: headerKey, value: error.response.headers[headerKey]}));
            myResponse.body = error.response.data;
          }
          else if (error.request) {
            alert("No response error")
          }
          else {
            alert("Error : " + error.message);
          }
        });
    },
  }
}
</script>