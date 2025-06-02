function showUpdateForm() {
    document.getElementById('content').innerHTML = `
        <h3>Змінити кількість товару</h3>
        <label>Оберіть категорію:</label>
        <select id="updateCategory" class="form-control mb-2" onchange="loadProductsForUpdate()">
            <option value="">-- Виберіть --</option>
            ${categories.map(cat => `<option value="${cat}">${cat}</option>`).join('')}
        </select>
        <form id="updateForm" onsubmit="event.preventDefault(); updateProduct();" style="display:none;">
            <label>Оберіть товар:</label>
            <select id="productId" class="form-control mb-2" required></select>
            <label>Нова кількість:</label>
            <input id="newQuantity" type="number" class="form-control mb-2" required>
            <button class="btn btn-warning">Змінити кількість</button>
            <button class="btn btn-secondary" type="button" onclick="goBack()">Назад</button>
        </form>
    `;
}
async function loadProductsForUpdate() {
    const selectedCategory = document.getElementById('updateCategory').value;
    const form = document.getElementById('updateForm');
    const select = document.getElementById('productId');

    if (!selectedCategory) {
        form.style.display = 'none';
        return;
    }

    try {
        const res = await api.get('/products');
        const filtered = res.data.filter(p => p.category === selectedCategory);

        if (filtered.length === 0) {
            alert("У цій категорії немає товарів.");
            form.style.display = 'none';
            return;
        }

        select.innerHTML = filtered.map(p => `
            <option value="${p.id}">${p.manufacturer} ${p.modelName} (кількість: ${p.quantityInStock})</option>
        `).join('');
        form.style.display = 'block';
    } catch (e) {
        alert("Помилка при завантаженні товарів: " + (e.response?.data?.message || e.message));
    }
}
    async function updateProduct() {
        try {
            const id = document.getElementById('productId').value;
            const quantityValue = document.getElementById('newQuantity').value;

            if (quantityValue === '' || isNaN(quantityValue)) {
                alert("Будь ласка, введіть правильне числове значення");
                return;
            }

            await api.put(`/products/${id}`, { quantityInStock: parseInt(quantityValue, 10) });
            alert("Кількість товару оновлено");
            goBack();
        } catch (e) {
            alert("Помилка при оновленні кількості: " + (e.response?.data?.message || e.message));
        }
    }