export const inputComponents = [
    {
        label: '单行文本',
        tag: 'lay-input',
        placeholder: '请输入',
        disabled: false,
        modelValue: 'inputVal',
        'allow-clear': false,
        "prefix-icon": '',
        "suffix-icon": '',
        maxlength: null,
    },
    {
        label: '文本域',
        tag: 'lay-textarea',
        placeholder: '请输入',
        "show-count": true,
        disabled: false,
        "v-model": 'textareaVal',
        maxlength: null,
    },
    {
        label: '密码框',
        tag: 'lay-input',
        placeholder: '请输入',
        disabled: false,
        modelValue: 'pwdVal',
        'allow-clear': false,
        "prefix-icon": '',
        "suffix-icon": '',
        maxlength: null,
        password: true
    },
    {
        label: '数字输入框',
        tag: 'lay-input-number',
        "v-model": 'numVal',
        disabledInput: false,
        disabled: false,
        step: 1,
        "step-strictly": false,
        precision: 0,
        min: '0',
        max: '100',
        indicator: ''
    }
]

export const selectComponents = [
    {
        label: '下拉选择',
        tag: 'lay-select',
        'v-model': 'selectVal',
        placeholder: '请选择',
        allowClear: false,
        disabled: false,
        multiple: false,
        showSearch: false,
        searchPlaceholder: '',
        options: [
            {
                label: "选项1",
                value: 1
            },
            {
                label: '选项2',
                value: 2
            }
        ]
    },
    {
        label: '级联选择',
        tag: 'lay-cascader',
        placeholder: "请选择",
        modelValue: 'cascaderVal',
        decollator: '/',
        onlyLastLevel: false,
        "allow-clear": false,
        disabled: false,
        options: [
            {
                label: '选项1',
                value: 1,
                children: [
                    {
                        label: '选项1-1',
                        value: '1-1'
                    },
                    {
                        label: '选项1-2',
                        value: '1-2'
                    }
                ]
            },
            {
                label: '选项2',
                value: 2,
                children: [
                    {
                        label: '选项2-1',
                        value: '2-1'
                    },
                    {
                        label: '选项2-2',
                        value: '2-2'
                    }
                ]
            }
        ]
    },
    {
        label: '单选框',
        tag: 'lay-radio',
        modelValue: 'radioVal',
        disabled: false,
        options: [
            {
                label: '选项1',
                value: 1
            },
            {
                label: '选项2',
                value: 2
            }
        ]
    },
    {
        label: '单选按钮',
        tag: 'lay-radio-button',
        modelValue: 'radioBtnVal',
        disabled: false,
        options: [
            {
                label: '选项1',
                value: 1
            },
            {
                label: '选项2',
                value: 2
            }
        ]
    },
    {
        label: '多选框',
        tag: 'lay-checkbox',
        skin: 'primary',
        "v-model": 'checkboxVal',
        disabled: false,
        options: [
            {
                label: '选项1',
                value: 1
            },
            {
                label: '选项2',
                value: 2
            }
        ]
    },
    {
        label: '开关',
        tag: 'lay-switch',
        "v-model": 'switchVal',
        disabled: false,
        "onswitch-text": '',
        "unswitch-text": '',
        "onswitch-value": '',
        "unswitch-value": '',
    },
    {
        label: '滑块',
        tag: 'lay-slider',
        "v-model": 'sliderVal',
        vertical: false,
        range: false,
        step: 1,
        min: 1,
        max: 100,
        disabled: false,
    },
    {
        label: '时间选择',
        tag: 'lay-date-picker',
        "v-model": 'dateVal',
        type: 'datetime',
        disabled: false,
        placeholder: "请选择",
        allowClear: false,
        simple: true,
        max: '',
        min: '',
        range: false,
        "prefix-icon": 'layui-icon-date',
        "suffix-icon": '',
        timestamp: false,
        format: 'YYYY-MM-DD',
    },
    {
        label: '评分',
        tag: 'lay-rate',
        "v-model":'rateVal',
        length: 5,
        readonly: false,
        theme: '#ffb800',
        half: false,
        text: false,
        allowClear: false
    },
    {
        label: '颜色选择',
        tag: 'lay-color-picker',
        "v-model":"colorVal",
        preset: '#16b777',
        disabled: false,
        allowClear: false,
        simple: false,

    },
    {
        label: '图标选择',
        tag: 'lay-icon-picker',
        "v-model": 'iconVal',
        page: true,
        showSearch: true,
        disabled: false,
        "allow-clear": false,
    },
    {
        label: '上传',
        tag: 'lay-upload',
        url: '',
        data: '',
        header: '',
        acceptMime: '',
        auto: true,
        field: 'file',
        drag: false,
        disabled: false,
        cut: false,
        text: '点击选择',
        dragText: '拖拽或点击上传',
    }
]

export const layoutComponents = [
    {
        label: '按钮',
        tag: 'lay-button',
        type:"primary",
        fluid:false,
        radius:false,
        border:'green',
        "border-style":'solid',
        disabled:false,
        loading:false,
        "prefix-icon":'',
        "suffix-icon":'',
        text:'按钮'
    }
]

