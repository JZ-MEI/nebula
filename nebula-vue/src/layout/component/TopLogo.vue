<template>
    <router-link :to="routerPath">
        <div class="menuTopLogo">
            <div class="topLogoImg"></div>
            <div class="topTitle" v-show="!collapse">星云管理系统</div>
        </div>
    </router-link>
</template>
<script>

export default {
    name: "TopLogo",
    props: {
        routerPath: String,
        collapse:Boolean,
        bgColor:String,
        titleColor:String
    }
}
</script>
<style>
.menuTopLogo {
    //background-color: #393d49;
    background-color: v-bind(bgColor);
    height: 45px;
    text-align: center;
    display: flex;
    align-items: center;
    justify-content: center;
}

.topLogoImg {
    height: 36px;
    width: 36px;
    //margin-top: 7px;
    background-image: url("@/assets/img/favicon.ico");
    //display: inline-block;
    background-size: cover;
}

.topTitle {
    font-size: 20px;
    margin-left: 10px;
    font-weight: bolder;
    color: v-bind(titleColor);
}
</style>