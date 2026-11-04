(function () {
  'use strict';
  var ctx = (document.querySelector('meta[name=ctx]') || {}).content || '/';
  var csrf = (document.querySelector('meta[name=_csrf]') || {}).content || '';

  /* ---------- toast ---------- */
  var toastEl, toastTimer;
  function toast(msg) {
    if (!toastEl) { toastEl = document.createElement('div'); toastEl.className = 'toast'; document.body.appendChild(toastEl); }
    toastEl.textContent = msg; toastEl.classList.add('show');
    clearTimeout(toastTimer); toastTimer = setTimeout(function () { toastEl.classList.remove('show'); }, 1800);
  }

  /* ---------- add to cart (no page reload) ---------- */
  window.addToCart = function (btn) {
    var id = btn.getAttribute('data-id');
    btn.disabled = true;
    fetch(ctx + 'cart/api/add/' + id, { method: 'POST', headers: { 'X-CSRF-TOKEN': csrf } })
      .then(function (r) { if (!r.ok) throw new Error(); return r.json(); })
      .then(function (d) {
        var c = document.getElementById('cartCount'); if (c) c.textContent = d.count;
        var card = btn.closest('.food-card'); var name = card ? card.getAttribute('data-name') : 'Item';
        toast(name + ' added to cart');
        var old = btn.textContent; btn.textContent = 'Added ✓'; btn.classList.add('added');
        setTimeout(function () { btn.textContent = old; btn.classList.remove('added'); }, 1200);
      })
      .catch(function () { toast('Could not add to cart. Please try again.'); })
      .then(function () { btn.disabled = false; });
  };

  document.addEventListener('DOMContentLoaded', function () {
    /* ---------- nav: active link, search, burger ---------- */
    var base = ctx.replace(/\/$/, ''), p = location.pathname;
    document.querySelectorAll('.nav-links a[data-match]').forEach(function (a) {
      var m = a.getAttribute('data-match');
      var on = m === '/' ? (p === base || p === base + '/') : p.indexOf(base + m) === 0;
      if (on) a.classList.add('active');
    });
    var st = document.getElementById('searchToggle'), sb = document.getElementById('searchbar');
    if (st && sb) st.addEventListener('click', function () {
      sb.classList.toggle('open'); if (sb.classList.contains('open')) sb.querySelector('input').focus();
    });
    var bg = document.getElementById('burger'), nl = document.getElementById('navLinks');
    if (bg && nl) bg.addEventListener('click', function () { nl.classList.toggle('open'); });

    /* ---------- wishlist hearts (saved in this browser) ---------- */
    var wish = [];
    try { wish = JSON.parse(localStorage.getItem('qb_wish') || '[]'); } catch (e) {}
    document.querySelectorAll('.heart').forEach(function (h) {
      var id = h.getAttribute('data-id');
      if (wish.indexOf(id) > -1) h.classList.add('on');
      h.addEventListener('click', function () {
        var i = wish.indexOf(id);
        if (i > -1) wish.splice(i, 1); else wish.push(id);
        h.classList.toggle('on', i === -1);
        try { localStorage.setItem('qb_wish', JSON.stringify(wish)); } catch (e) {}
        toast(i === -1 ? 'Saved to wishlist' : 'Removed from wishlist');
      });
    });

    /* ---------- restaurant menu: category / search / price / veg / sort ---------- */
    var grid = document.getElementById('menuGrid');
    if (!grid) return;
    var cards = Array.prototype.slice.call(grid.children);
    cards.forEach(function (c, i) { c.__idx = i; });
    var q = document.getElementById('menuSearch'), price = document.getElementById('priceSel'),
        sort = document.getElementById('sortSel'), veg = document.getElementById('vegOnly'),
        count = document.getElementById('resultCount'), empty = document.getElementById('emptyMsg'),
        pills = document.querySelectorAll('#pills .pill'), cat = 'all';

    function num(c, k) { return parseFloat(c.getAttribute('data-' + k)) || 0; }
    function apply() {
      var term = q.value.trim().toLowerCase(), max = parseFloat(price.value) || Infinity, shown = 0;
      cards.forEach(function (c) {
        var ok = (cat === 'all' || c.getAttribute('data-cat') === cat) &&
                 (!term || c.getAttribute('data-name').toLowerCase().indexOf(term) > -1) &&
                 num(c, 'price') <= max &&
                 (!veg.checked || c.getAttribute('data-veg') === 'true');
        c.style.display = ok ? '' : 'none'; if (ok) shown++;
      });
      var s = sort.value;
      cards.slice().sort(function (a, b) {
        if (s === 'price-asc') return num(a, 'price') - num(b, 'price');
        if (s === 'price-desc') return num(b, 'price') - num(a, 'price');
        if (s === 'rating') return num(b, 'rating') - num(a, 'rating');
        if (s === 'popular') return num(b, 'pop') - num(a, 'pop');
        return a.__idx - b.__idx;
      }).forEach(function (c) { grid.appendChild(c); });
      count.textContent = 'Showing ' + shown + ' of ' + cards.length + ' dishes';
      empty.hidden = shown !== 0;
    }
    pills.forEach(function (b) {
      b.addEventListener('click', function () {
        pills.forEach(function (x) { x.classList.remove('active'); });
        b.classList.add('active'); cat = b.getAttribute('data-cat'); apply();
      });
    });
    [q, price, sort, veg].forEach(function (el) { el.addEventListener('input', apply); el.addEventListener('change', apply); });
    apply();
  });
})();
