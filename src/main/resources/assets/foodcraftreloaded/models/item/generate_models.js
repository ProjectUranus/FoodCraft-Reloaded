const fs = require('fs');
const itemModel = 'resources/assets/foodcraftreloaded/models/item/';
fs.readdirSync(itemModel).filter(s => s.startsWith("ble_")).forEach(s => {
    fs.renameSync(itemModel + s, itemModel + s.substring(4))
})
