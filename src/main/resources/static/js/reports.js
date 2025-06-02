function showReportForm() {
    document.getElementById('content').innerHTML = `
        <h3>Формування звіту</h3>
        <form id="reportForm" onsubmit="event.preventDefault(); fetchReport();">
            <label for="reportType">Оберіть тип звіту:</label>
            <select id="reportType" class="form-control mb-2" onchange="adjustReportInputs()">
                <option value="QUANTITY_SOLD">Кількість проданих товарів за період</option>
                <option value="PROFIT_BY_CATEGORY">Прибуток по категоріях за період</option>
            </select>
            <div id="dateFields" class="mb-2">
                <label>Від:</label>
                <input id="fromDate" type="date" class="form-control mb-2" required>
                <label>До:</label>
                <input id="toDate" type="date" class="form-control mb-2" required>
            </div>

            <button class="btn btn-info">Показати звіт</button>
            <button class="btn btn-secondary" type="button" onclick="goBack()">Назад</button>
        </form>
        <div id="reportResult" class="mt-3"></div>
    `;
}
    function adjustReportInputs() {
    const type = document.getElementById('reportType').value;
    const dateFields = document.getElementById('dateFields');
    dateFields.style.display = 'block';
    document.getElementById('reportResult').innerText = '';
}
async function fetchReport() {
    const type = document.getElementById('reportType').value;
    const from = document.getElementById('fromDate').value;
    const to = document.getElementById('toDate').value;
    if (!from || !to) {
        alert("Будь ласка, виберіть дату 'від' та 'до'.");
        return;
    }
    try {
        let response;
        let resultHTML = "";
        if (type === 'QUANTITY_SOLD') {
            response = await api.get(`/reports/quantity-sold?from=${from}&to=${to}`);
            resultHTML = `<h5>Продано товарів: ${response.data}</h5>`;
        } else if (type === 'PROFIT_BY_CATEGORY') {
            response = await api.get(`/reports/profit-by-category?from=${from}&to=${to}`);
            resultHTML = `
                <h5>Прибуток по категоріях:</h5>
                <table class="table table-bordered">
                    <thead>
                        <tr>
                            <th>Категорія</th>
                            <th>Прибуток (грн)</th>
                        </tr>
                    </thead>
                    <tbody>
            `;
            for (const [category, profit] of Object.entries(response.data)) {
                resultHTML += `
                    <tr>
                        <td>${category}</td>
                        <td>${parseFloat(profit).toFixed(2)}</td>
                    </tr>
                `;
            }
            resultHTML += `
                    </tbody>
                </table>
            `;
        }
        document.getElementById('reportResult').innerHTML = resultHTML;
    } catch (e) {
        alert("Помилка при отриманні звіту: " + (e.response?.data?.message || e.message));
    }
}
