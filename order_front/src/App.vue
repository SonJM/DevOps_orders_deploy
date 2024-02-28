<template>
  <HeaderComponent/>
  <div>
    <router-view/>
  </div>
  <FooterComponent/>
</template>

<script>
import HeaderComponent from "@/components/HeaderComponent.vue";
import FooterComponent from "@/components/FooterComponent.vue";
export default {
  components: {
    FooterComponent,
    HeaderComponent,
  }
}
</script>