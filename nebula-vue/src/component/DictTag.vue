<template>
    <template v-for="item in options">
        <template v-if="String(values)===String(item.dictKey)">
            <span v-if="item.useTag === '0'|| item.useTag === 0">{{ item.dictValue }}</span>
            <lay-tag v-else-if="item.useTag==='1'||item.useTag=== 1" :type="item.tagType"  variant="light">{{ item.dictValue }}</lay-tag>
        </template>
    </template>
</template>
<script>
export default {
    name: "DictTag",
    props: {
        options: Object,
        values: [Number,String]
    }
}
</script>