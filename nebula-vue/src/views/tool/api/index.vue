<template>
    <div class="view-container">
        test
    </div>
</template>
<script>
import {getApiList} from "@/api/api.js";

export default {
    setup() {
        function loadApiList() {
            getApiList().then(res => {
                console.log(res)
            })
        }

        onMounted(()=>{
            loadApiList()
        })
    }

}
</script>