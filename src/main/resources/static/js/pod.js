
    eventForPodPage();
    addPodButton();


function searchPod(event) {
    event.preventDefault();
    console.dir(event.target)
    let searchText = document.querySelector("#search_table").value.trim();
    const param = new URLSearchParams({
        "filterText": searchText,
    });
    let href = event.target.getAttribute('href')
    fetch(href + '?' + param).then(response => response.text()).then(fragment => {
            document.querySelector(".pod_list").innerHTML = fragment
            eventForPodPage()
        }
    )
}

function addPodButton() {
    document.getElementById('addBtn').addEventListener('click', function (event) {
        event.preventDefault();
        let href = this.getAttribute('href');
        fetch(href).then(response => response.text()).then(fragment => {
            document.querySelector("#addModal").innerHTML = fragment;
        }).then(() => {
            let model = new bootstrap.Modal(document.getElementById('addModal'), {});
            model.show();
            document.getElementById("add_pod")
                .addEventListener('submit', event => submitNewPodForm(event))
        });
    });
}

function editEvent(el) {
    el.addEventListener('click', function (event) {
        event.preventDefault()
        let href = this.getAttribute('href')
        editAsyncFetch(href)
    })
}

function editEventRow(el) {
    el.addEventListener('dblclick', function (event) {
        event.preventDefault()
        let editBtn = el.querySelector('.editBtn')
        let href = editBtn.getAttribute('href')
        editAsyncFetch(href)
    })
}

function editAsyncFetch(href) {
    fetch(href).then(response => response.text()).then(fragment => {
        document.querySelector("#editModal").innerHTML = fragment;
    }).then(() => {
        let model = new bootstrap.Modal(document.getElementById('editModal'), {});
        model.show();
        document.getElementById("edit_pod")
            .addEventListener('submit', event => submitEditPodForm(event))
    });
}

function eventForPodPage() {
    document.querySelectorAll('.table .editBtn')
        .forEach(editBtn => editEvent(editBtn));

    document.querySelectorAll('.table tr')
        .forEach(tr => editEventRow(tr));

    document.querySelectorAll('.table .deleteBtn')
        .forEach(deleteBtn => deleteBtn.addEventListener('click', function (event) {
            event.preventDefault();
            let href = this.getAttribute('href');
            document.querySelector('#deleteModal .modal-footer a').setAttribute('href', href)
            let model = new bootstrap.Modal(document.getElementById('deleteModal'), {});
            model.show();
            let delPod = document.getElementById('del_pod');
            delPod.addEventListener('click', ev => deletePod(ev))
        }))
}

async function deletePod(event) {
    event.preventDefault();
    let el = event.target;
    let href = el.getAttribute('href');
    fetch(href).then(response => response.text()).then(fragment => {
        let modal = bootstrap.Modal.getInstance(document.getElementById('deleteModal'))
        modal.hide();
        document.querySelector(".pod_list").innerHTML = fragment;
        eventForPodPage();
    })
}

async function submitNewPodForm(event) {
    event.preventDefault();
    let formData = new FormData(event.target),
        request = new Request(event.target.action, {
            method: 'POST',
            body: formData,
        });
    savePod(request)
}

function savePod(request) {
    fetch(request).then(response => response.text()).then(fr => {
        let modal = bootstrap.Modal.getInstance(document.getElementById('addModal'))
        modal.hide();
        document.querySelector(".pod_list").innerHTML = fr;
        eventForPodPage();
    });
}

async function submitEditPodForm(event) {
    event.preventDefault();
    let formData = new FormData(event.target),
        request = new Request(event.target.action, {
            method: 'POST',
            body: formData
        });
    let response = await fetch(request);
    let podTable = await response.text();

    let modal = bootstrap.Modal.getInstance(document.getElementById('editModal'))
    modal.hide();
    document.querySelector(".pod_list").innerHTML = podTable;
    eventForPodPage();
}