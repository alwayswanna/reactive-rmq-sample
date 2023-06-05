<script setup>
import SendDataView from './components/SendDataView.vue'
</script>

<template>
    <header>
        <img alt="Vue logo" class="logo" src="./assets/an-logo.png" width="125" height="125"/>
        <div class="wrapper"><h1>Send user message to backend server:</h1></div>
    </header>

    <main><SendDataView/></main>
</template>

<style scoped>
header {
    line-height: 1.5;
}

.logo {
    display: block;
    margin: 0 auto 2rem;
}

@media (min-width: 1024px) {
    header {
        display: flex;
        place-items: center;
        padding-right: calc(var(--section-gap) / 2);
    }

    .logo {
        margin: 0 2rem 0 0;
    }

    header .wrapper {
        display: flex;
        place-items: flex-start;
        flex-wrap: wrap;
    }
}
</style>
