<template>
  <div class="api">
    <v-row>
      <v-col class="request" cols="12" sm="6">
        <v-card>
          <v-card-title>REQUEST</v-card-title>
          <v-card-text>

          </v-card-text>
        </v-card>
      </v-col>
      <v-spacer></v-spacer>
      <v-col class="response" cols="12" sm="6">
        <v-card>
          <v-card-title>RESPONSE</v-card-title>
          <v-card-text>

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
