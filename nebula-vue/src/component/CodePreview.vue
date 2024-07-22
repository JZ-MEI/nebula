<template>
    <div ref="editorContainer" class="monaco-editor-container"></div>
</template>

<script>
import { onMounted, onBeforeUnmount, ref } from 'vue';
import * as monaco from 'monaco-editor';

export default {
    name: 'CodePreview',
    props: {
        value: {
            type: String,
            required: true,
        },
        language: {
            type: String,
            default: 'html',
        },
        theme: {
            type: String,
            default: 'vs-dark',
        },
    },
    emits: ['update:value'],
    setup(props, { emit }) {
        const editorContainer = ref(null);
        let editorInstance;

        onMounted(() => {
            editorInstance = monaco.editor.create(editorContainer.value, {
                value: props.value,
                language: props.language,
                theme: props.theme,
            });

            editorInstance.onDidChangeModelContent(() => {
                emit('update:value', editorInstance.getValue());
            });
        });

        onBeforeUnmount(() => {
            if (editorInstance) {
                editorInstance.dispose();
            }
        });

        return {
            editorContainer,
        };
    },
};
</script>

<style>
.monaco-editor-container {
    width: 100%;
    height: 100%;
}
</style>