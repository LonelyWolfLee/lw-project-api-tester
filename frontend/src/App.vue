<template>
  <v-app id="inspire">
    <v-navigation-drawer
        v-model="drawer"
        app
    >
      <v-sheet
          color="primary lighten-3"
          class="pa-10"
      >
        <h1>Version : v 0.1</h1>
      </v-sheet>

      <v-divider></v-divider>

      <v-list>
        <v-list-item
            v-for="[icon, text, link] in links"
            :key="icon"
            :to="link"
        >
          <v-list-item-icon>
            <v-icon>{{ icon }}</v-icon>
          </v-list-item-icon>

          <v-list-item-content>
            <v-list-item-title>{{ text }}</v-list-item-title>
          </v-list-item-content>
        </v-list-item>
      </v-list>
    </v-navigation-drawer>

    <v-app-bar app color="primary" dark>
      <v-app-bar-nav-icon @click="drawer = !drawer"><v-icon>{{ icons.mdiMenu }}</v-icon></v-app-bar-nav-icon>
      <v-toolbar-title>LW API TESTER</v-toolbar-title>
    </v-app-bar>

    <v-main>
      <router-view />
    </v-main>
  </v-app>
</template>

<script>
import { mdiApi } from "@mdi/js"
import { mdiMenu } from '@mdi/js';
export default {
  name: 'App',
  data: () => ({
    icons: {
      mdiMenu
    },
    cards: ['Today', 'Yesterday'],
    drawer: null,
    links: [
      [mdiApi, 'Call Test', '/call'],
      // ['mdi-history', 'Call History', '/history'],
    ],
  }),
}
</script>