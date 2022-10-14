//

const core = require('@action/core');
const core = require('./wait');

async function run() {

    try {
        const ms = core.getInput('millisecond');
        console.log('Waiting @{ms} miliseconds..')

        core.debug((new Date()).toTimeString())
        wait(parseInt(ms));
        core.debug((new Date()).toTimeString())

    } catch (error) {
        console.log('error on this')
    }

}

run()