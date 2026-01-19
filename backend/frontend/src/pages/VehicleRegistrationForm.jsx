// frontend/src/pages/VehicleRegistrationForm.jsx
import { useMemo, useState } from "react";
import { documentApi } from "../api/documentApi";

export default function VehicleRegistrationForm() {
  const [form, setForm] = useState({
    plate: "",
    owner: "",
    frameNo: "",
    engineNo: "",
  });

  const [file, setFile] = useState(null);
  const [raw, setRaw] = useState("");
  const [err, setErr] = useState("");
  const [loading, setLoading] = useState(false);

  const preview = useMemo(
    () => (file ? URL.createObjectURL(file) : ""),
    [file]
  );

  const setField = (k, v) => setForm((p) => ({ ...p, [k]: v }));

  const onOcr = async () => {
    setErr("");
    setRaw("");
    if (!file) return setErr("Chọn ảnh cavet trước đã");

    try {
      setLoading(true);
      const data = await documentApi.scanVehicleRegistration(file); // ✅ đúng vì interceptor trả res.data
      const rawJson = data?.raw || "";

      setRaw(rawJson);
      if (!rawJson) return;

      const parsed = JSON.parse(rawJson);
      const d = parsed?.data || {};

      setForm((prev) => ({
        ...prev,
        plate: d.plate ?? prev.plate,
        owner: d.owner ?? prev.owner,
        frameNo: d.frame_no ?? d.frameNo ?? prev.frameNo,
        engineNo: d.engine_no ?? d.engineNo ?? prev.engineNo,
      }));
    } catch (e) {
      setErr(
        e?.response?.data?.message ||
          e?.response?.data ||
          e?.message ||
          "OCR lỗi"
      );
    } finally {
      setLoading(false);
    }
  };
  const [saving, setSaving] = useState(false);

  const onSave = async () => {
    setErr("");
    try {
      setSaving(true);

      // raw đang là string JSON; lưu luôn vào DB để audit
      const payload = {
        plate: form.plate,
        owner: form.owner,
        frameNo: form.frameNo,
        engineNo: form.engineNo,
        rawJson: raw,
      };

      const res = await documentApi.saveVehicleRegistration(payload); // ✅ res.data đã được interceptor trả về
      alert(`Lưu thành công! ID: ${res?.id ?? "N/A"}`);
    } catch (e) {
      setErr(
        e?.response?.data?.message ||
          e?.response?.data ||
          e?.message ||
          "Lưu lỗi"
      );
    } finally {
      setSaving(false);
    }
  };

  const exportJson = () => {
    const blob = new Blob([JSON.stringify({ form, raw }, null, 2)], {
      type: "application/json",
    });
    const url = URL.createObjectURL(blob);
    const a = document.createElement("a");
    a.href = url;
    a.download = `cavet_${form.plate || "unknown"}.json`;
    a.click();
    URL.revokeObjectURL(url);
  };

  const exportCsv = () => {
    const rows = [
      ["plate", "owner", "frameNo", "engineNo"],
      [form.plate, form.owner, form.frameNo, form.engineNo],
    ];
    const csv = rows
      .map((r) =>
        r.map((v) => `"${String(v ?? "").replaceAll('"', '""')}"`).join(",")
      )
      .join("\n");

    const blob = new Blob([csv], { type: "text/csv;charset=utf-8" });
    const url = URL.createObjectURL(blob);
    const a = document.createElement("a");
    a.href = url;
    a.download = `cavet_${form.plate || "unknown"}.csv`;
    a.click();
    URL.revokeObjectURL(url);
  };

  return (
    <div className="p-6 space-y-4 relative z-10">
      <h2 className="text-xl font-bold">Nhập cavet xe (OCR)</h2>

      <input
        type="file"
        accept="image/*"
        onChange={(e) => setFile(e.target.files?.[0] || null)}
      />

      {preview && (
        <img
          src={preview}
          alt="preview"
          className="rounded-xl border border-gray-200 max-h-72 object-contain w-full bg-black/5"
        />
      )}

      <button
        onClick={onOcr}
        disabled={!file || loading}
        className="px-4 py-2 rounded bg-black text-white font-bold disabled:opacity-50"
      >
        {loading ? "Đang OCR..." : "OCR cavet"}
      </button>

      {err && <div className="text-red-600 font-semibold">{err}</div>}

      <div className="grid grid-cols-1 md:grid-cols-2 gap-3">
        <Field
          label="Biển số"
          value={form.plate}
          onChange={(v) => setField("plate", v)}
        />
        <Field
          label="Chủ xe"
          value={form.owner}
          onChange={(v) => setField("owner", v)}
        />
        <Field
          label="Số khung"
          value={form.frameNo}
          onChange={(v) => setField("frameNo", v)}
        />
        <Field
          label="Số máy"
          value={form.engineNo}
          onChange={(v) => setField("engineNo", v)}
        />
      </div>
      <div className="flex flex-wrap gap-2">
        <button
          onClick={onSave}
          disabled={saving || loading}
          className="px-4 py-2 rounded bg-green-600 text-white font-bold disabled:opacity-50"
        >
          {saving ? "Đang lưu..." : "Lưu vào DB"}
        </button>

        <button
          onClick={exportJson}
          disabled={!raw}
          className="px-4 py-2 rounded bg-blue-600 text-white font-bold disabled:opacity-50"
        >
          Xuất JSON
        </button>

        <button
          onClick={exportCsv}
          disabled={!raw}
          className="px-4 py-2 rounded bg-indigo-600 text-white font-bold disabled:opacity-50"
        >
          Xuất CSV
        </button>
      </div>

      {raw && (
        <details className="bg-gray-100 p-3 rounded">
          <summary className="cursor-pointer font-semibold">
            Xem raw OCR JSON
          </summary>
          <pre className="overflow-auto text-sm mt-2">{raw}</pre>
        </details>
      )}
    </div>
  );
}

function Field({ label, value, onChange }) {
  return (
    <label className="space-y-1 block">
      <div className="text-sm text-gray-600">{label}</div>
      <input
        type="text"
        className="w-full border rounded px-3 py-2"
        value={value ?? ""}
        onChange={(e) => onChange(e.target.value)}
      />
    </label>
  );
}
